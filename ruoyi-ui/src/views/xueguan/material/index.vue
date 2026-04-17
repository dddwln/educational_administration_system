<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true" label-width="88px">
      <el-form-item label="关键字">
        <el-input v-model="queryParams.title" placeholder="搜索标题/姓名/编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="上传角色">
        <el-select v-model="queryParams.ownerRole" placeholder="全部" clearable>
          <el-option label="教师" value="teacher" />
          <el-option label="学生" value="student" />
        </el-select>
      </el-form-item>
      <el-form-item label="材料分类">
        <el-select v-model="queryParams.materialCategory" placeholder="全部" clearable filterable>
          <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态">
        <el-select v-model="queryParams.auditStatus" placeholder="全部" clearable>
          <el-option label="待审核" value="0" />
          <el-option label="已通过" value="1" />
          <el-option label="已驳回" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['xg:material:add']">上传材料</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['xg:material:remove']">删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="材料标题" prop="title" min-width="180" :show-overflow-tooltip="true" />
      <el-table-column label="上传角色" prop="ownerRole" width="90">
        <template slot-scope="scope">{{ roleLabel(scope.row.ownerRole) }}</template>
      </el-table-column>
      <el-table-column label="材料分类" prop="materialCategory" width="160">
        <template slot-scope="scope">{{ categoryLabel(scope.row.materialCategory) }}</template>
      </el-table-column>
      <el-table-column label="具体项摘要" min-width="260" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-popover placement="top-start" width="420" trigger="hover">
            <div class="detail-popover">
              <p v-for="item in detailPairs(scope.row)" :key="item.label"><b>{{ item.label }}：</b>{{ item.value }}</p>
            </div>
            <span slot="reference">{{ detailSummary(scope.row) }}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="附件" min-width="150" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <a v-if="scope.row.fileName" :href="downloadUrl(scope.row.fileName)" target="_blank">查看附件</a>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" prop="auditStatus" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.auditStatus === '0'" type="warning">待审核</el-tag>
          <el-tag v-else-if="scope.row.auditStatus === '1'" type="success">已通过</el-tag>
          <el-tag v-else type="danger">已驳回</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="上传时间" prop="createTime" width="160" />
      <el-table-column label="操作" width="180" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['xg:material:edit']">修改</el-button>
          <el-button v-if="scope.row.auditStatus === '0'" size="mini" type="text" icon="el-icon-check" @click="handleAudit(scope.row, '1')" v-hasPermi="['xg:material:audit']">通过</el-button>
          <el-button v-if="scope.row.auditStatus === '0'" size="mini" type="text" icon="el-icon-close" @click="handleReject(scope.row)" v-hasPermi="['xg:material:audit']">驳回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="820px" append-to-body>
      <el-form ref="form" :model="form" label-width="130px">
        <el-form-item label="材料分类" required>
          <el-select v-model="form.materialCategory" placeholder="请选择材料分类" filterable @change="handleCategoryChange">
            <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>

        <template v-if="activeFields.length">
          <el-row :gutter="16">
            <el-col v-for="field in activeFields" :key="field.prop" :span="field.span || 12">
              <el-form-item :label="field.label" :required="field.required !== false">
                <el-input v-if="field.type === 'input'" v-model="detailData[field.prop]" :placeholder="'请输入' + field.label" />
                <el-input v-else-if="field.type === 'textarea'" v-model="detailData[field.prop]" type="textarea" :rows="3" :placeholder="'请输入' + field.label" />
                <el-input-number v-else-if="field.type === 'number'" v-model="detailData[field.prop]" :min="0" :precision="2" style="width: 100%" />
                <el-date-picker v-else-if="field.type === 'date'" v-model="detailData[field.prop]" type="date" value-format="yyyy-MM-dd" :placeholder="'请选择' + field.label" style="width: 100%" />
                <el-select v-else-if="field.type === 'select'" v-model="detailData[field.prop]" :placeholder="'请选择' + field.label" style="width: 100%">
                  <el-option v-for="option in field.options" :key="option" :label="option" :value="option" />
                </el-select>
                <file-upload v-else-if="field.type === 'file'" v-model="detailData[field.prop]" :limit="1" :file-size="200" :file-type="['doc','docx','pdf','zip','png','jpg','jpeg']" />
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <el-form-item label="通用附件">
          <file-upload v-model="form.fileName" :limit="1" :file-size="200" :file-type="['doc','docx','pdf','zip','xls','xlsx','png','jpg','jpeg']" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remarks" type="textarea" :rows="3" placeholder="补充说明，可不填" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">提交审核</el-button>
        <el-button @click="open = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaterial, addMaterial, updateMaterial, delMaterial, auditMaterial } from '@/api/xueguan/material'

const CATEGORY_CONFIGS = [
  {
    value: 'competition',
    label: '竞赛材料',
    titleFields: ['studentName', 'compSubject', 'awardName'],
    dateField: 'awardDate',
    fields: [
      { prop: 'studentNo', label: '学号', type: 'input' },
      { prop: 'studentName', label: '姓名', type: 'input' },
      { prop: 'competitionType', label: '赛类', type: 'input' },
      { prop: 'awardLevel', label: '奖项等级', type: 'select', options: ['国家级', '省级', '市级', '校级', '院级'] },
      { prop: 'compSubject', label: '比赛科目', type: 'input' },
      { prop: 'mentorName', label: '指导老师', type: 'input' },
      { prop: 'awardName', label: '奖项', type: 'input' },
      { prop: 'awardDate', label: '获奖日期', type: 'date', required: false }
    ]
  },
  {
    value: 'research_award',
    label: '科研奖励材料',
    titleFields: ['rewardType', 'relatedPersons'],
    amountField: 'rewardAmount',
    dateField: 'obtainDate',
    fields: [
      { prop: 'rewardType', label: '奖励类型', type: 'input' },
      { prop: 'rewardAmount', label: '奖励金额', type: 'number' },
      { prop: 'obtainDate', label: '获得时间', type: 'date' },
      { prop: 'relatedPersons', label: '相关人员', type: 'textarea', span: 24 }
    ]
  },
  {
    value: 'research_project',
    label: '科研项目材料',
    titleFields: ['projectNo', 'projectName'],
    dateField: 'projectStartDate',
    projectNoField: 'projectNo',
    fields: [
      { prop: 'projectNo', label: '项目编号', type: 'input' },
      { prop: 'projectName', label: '项目名称', type: 'input' },
      { prop: 'projectType', label: '项目类型', type: 'select', options: ['纵向项目', '横向项目', '校级项目', '院级项目', '其他'] },
      { prop: 'leaderName', label: '项目负责人', type: 'input' },
      { prop: 'projectStartDate', label: '项目开始时间', type: 'date' },
      { prop: 'projectEndDate', label: '项目结束时间', type: 'date' },
      { prop: 'projectAmount', label: '项目金额', type: 'number', required: false }
    ]
  },
  {
    value: 'faculty_info',
    label: '教职工信息材料',
    titleFields: ['facultyNo', 'facultyName'],
    dateField: 'entryDate',
    fields: [
      { prop: 'facultyNo', label: '教职工号', type: 'input' },
      { prop: 'facultyName', label: '姓名', type: 'input' },
      { prop: 'gender', label: '性别', type: 'select', options: ['男', '女'] },
      { prop: 'birthDate', label: '出生日期', type: 'date' },
      { prop: 'college', label: '所在学院', type: 'input' },
      { prop: 'entryDate', label: '入职时间', type: 'date' },
      { prop: 'professionalTitle', label: '专业职务', type: 'input' },
      { prop: 'highestEducation', label: '最高学历', type: 'input' }
    ]
  },
  {
    value: 'education_history',
    label: '学习经历材料',
    titleFields: ['facultyNo', 'schoolName'],
    dateField: 'startDate',
    fields: [
      { prop: 'facultyNo', label: '教职工号', type: 'input' },
      { prop: 'schoolName', label: '入学学校', type: 'input' },
      { prop: 'major', label: '所学专业', type: 'input' },
      { prop: 'startDate', label: '入学日期', type: 'date' },
      { prop: 'endDate', label: '毕业日期', type: 'date' },
      { prop: 'degree', label: '获得学位', type: 'input' },
      { prop: 'certificatePath', label: '证书扫描件', type: 'file', required: false, span: 24 }
    ]
  },
  {
    value: 'personal_honor',
    label: '个人荣誉称号材料',
    titleFields: ['honorName', 'grantUnit'],
    dateField: 'obtainDate',
    fields: [
      { prop: 'honorName', label: '荣誉称号名称', type: 'input' },
      { prop: 'obtainDate', label: '获得日期', type: 'date' },
      { prop: 'grantUnit', label: '授予单位', type: 'input' }
    ]
  },
  {
    value: 'entrepreneurship',
    label: '个人创业情况材料',
    titleFields: ['companyName', 'legalPerson'],
    dateField: 'establishDate',
    fields: [
      { prop: 'companyName', label: '创办公司名称', type: 'input' },
      { prop: 'legalPerson', label: '法人', type: 'input' },
      { prop: 'establishDate', label: '成立日期', type: 'date' },
      { prop: 'businessScope', label: '经营范围', type: 'textarea', required: false, span: 24 }
    ]
  },
  {
    value: 'part_time',
    label: '个人兼职情况材料',
    titleFields: ['socialPartTime', 'researchField'],
    fields: [
      { prop: 'socialPartTime', label: '社会兼职', type: 'textarea', span: 24 },
      { prop: 'researchField', label: '研究领域', type: 'textarea', span: 24 },
      { prop: 'honors', label: '荣誉', type: 'textarea', span: 24 },
      { prop: 'visitInfo', label: '留学访学信息', type: 'textarea', span: 24 }
    ]
  },
  {
    value: 'new_teacher',
    label: '新入职教师记录材料',
    titleFields: ['teacherName', 'teacherCategory'],
    dateField: 'entryDate',
    fields: [
      { prop: 'teacherName', label: '教师姓名', type: 'input' },
      { prop: 'entryDate', label: '入职日期', type: 'date' },
      { prop: 'regularDate', label: '转正日期', type: 'date' },
      { prop: 'teacherCategory', label: '教师类别', type: 'select', options: ['专任教师', '实验教师', '行政兼课', '引进人才', '其他'] }
    ]
  },
  {
    value: 'distinguished_teacher',
    label: '特聘教师记录材料',
    titleFields: ['teacherName', 'appointLevel'],
    dateField: 'contractDate',
    fields: [
      { prop: 'teacherName', label: '教师姓名', type: 'input' },
      { prop: 'appointLevel', label: '特聘层次', type: 'input' },
      { prop: 'contractDate', label: '特聘合同日期', type: 'date' },
      { prop: 'contractPath', label: '合同上传路径', type: 'file', span: 24 }
    ]
  },
  {
    value: 'public_institution_assessment',
    label: '历年事业单位考核结果材料',
    titleFields: ['teacherName', 'assessmentResult'],
    dateField: 'assessmentDate',
    fields: [
      { prop: 'teacherName', label: '教师姓名', type: 'input' },
      { prop: 'assessmentDate', label: '考核日期', type: 'date' },
      { prop: 'assessmentResult', label: '考核结果', type: 'select', options: ['优秀', '合格', '基本合格', '不合格'] }
    ]
  },
  {
    value: 'maternal_child',
    label: '妇幼信息材料',
    titleFields: ['childName'],
    dateField: 'birthDate',
    fields: [
      { prop: 'childName', label: '子女姓名', type: 'input' },
      { prop: 'birthDate', label: '出生日期', type: 'date' },
      { prop: 'guardianName', label: '监护人姓名', type: 'input', required: false },
      { prop: 'serviceNote', label: '服务备注', type: 'textarea', required: false, span: 24 }
    ]
  }
]

export default {
  name: 'XgMaterial',
  data() {
    return {
      loading: false,
      ids: [],
      multiple: true,
      open: false,
      title: '',
      total: 0,
      materialList: [],
      categoryOptions: CATEGORY_CONFIGS.map(item => ({ value: item.value, label: item.label })),
      detailData: {},
      queryParams: { pageNum: 1, pageSize: 10, title: undefined, ownerRole: undefined, materialCategory: undefined, auditStatus: undefined },
      form: {}
    }
  },
  computed: {
    activeConfig() {
      return CATEGORY_CONFIGS.find(item => item.value === this.form.materialCategory)
    },
    activeFields() {
      return this.activeConfig ? this.activeConfig.fields : []
    }
  },
  created() {
    if (this.$route.query.audit === '1') {
      this.queryParams.auditStatus = '0'
    }
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listMaterial(this.queryParams).then(res => {
        this.materialList = res.rows
        this.total = res.total
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10 }
      this.getList()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.materialId)
      this.multiple = !selection.length
    },
    handleAdd() {
      this.form = { auditStatus: '0', materialCategory: undefined, materialType: undefined }
      this.detailData = {}
      this.title = '上传材料'
      this.open = true
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row)
      this.detailData = this.parseDetailData(row)
      this.ensureDetailDataKeys()
      this.title = '修改材料'
      this.open = true
    },
    handleCategoryChange() {
      this.form.materialType = this.categoryLabel(this.form.materialCategory)
      this.detailData = {}
      this.ensureDetailDataKeys()
    },
    ensureDetailDataKeys() {
      this.activeFields.forEach(field => {
        if (this.detailData[field.prop] === undefined) {
          this.$set(this.detailData, field.prop, field.type === 'number' ? undefined : '')
        }
      })
    },
    submitForm() {
      if (!this.form.materialCategory) {
        this.$modal.msgError('请选择材料分类')
        return
      }
      const missing = this.activeFields.find(field => field.required !== false && (this.detailData[field.prop] === undefined || this.detailData[field.prop] === null || this.detailData[field.prop] === ''))
      if (missing) {
        this.$modal.msgError(`${missing.label}不能为空`)
        return
      }
      const config = this.activeConfig
      const data = Object.assign({}, this.form)
      data.materialType = config.label
      data.title = this.buildTitle(config)
      data.materialData = JSON.stringify(this.detailData)
      data.amount = config.amountField ? this.detailData[config.amountField] : null
      data.materialDate = config.dateField ? this.detailData[config.dateField] : null
      data.projectNo = config.projectNoField ? this.detailData[config.projectNoField] : null
      const api = data.materialId ? updateMaterial : addMaterial
      api(data).then(() => {
        this.$modal.msgSuccess('提交成功')
        this.open = false
        this.getList()
      })
    },
    buildTitle(config) {
      const parts = (config.titleFields || [])
        .map(key => this.detailData[key])
        .filter(value => value !== undefined && value !== null && value !== '')
      return parts.length ? `${config.label}-${parts.join('-')}` : config.label
    },
    handleDelete() {
      this.$modal.confirm('确认删除选中的材料吗？').then(() => delMaterial(this.ids)).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      })
    },
    handleAudit(row, status) {
      auditMaterial(row.materialId, status).then(() => {
        this.$modal.msgSuccess('审核通过')
        this.getList()
      })
    },
    handleReject(row) {
      this.$prompt('请输入驳回原因', '驳回材料', { inputType: 'textarea' }).then(({ value }) => {
        auditMaterial(row.materialId, '2', value).then(() => {
          this.$modal.msgSuccess('已驳回')
          this.getList()
        })
      })
    },
    parseDetailData(row) {
      if (!row.materialData) return {}
      try {
        return JSON.parse(row.materialData)
      } catch (e) {
        return {}
      }
    },
    detailPairs(row) {
      const config = CATEGORY_CONFIGS.find(item => item.value === row.materialCategory)
      const detail = this.parseDetailData(row)
      if (!config) return []
      return config.fields
        .map(field => ({ label: field.label, value: detail[field.prop] }))
        .filter(item => item.value !== undefined && item.value !== null && item.value !== '')
    },
    detailSummary(row) {
      const pairs = this.detailPairs(row).slice(0, 3)
      return pairs.length ? pairs.map(item => `${item.label}:${item.value}`).join('；') : '暂无明细'
    },
    roleLabel(role) {
      return role === 'teacher' ? '教师' : role === 'student' ? '学生' : '管理员'
    },
    categoryLabel(category) {
      const item = CATEGORY_CONFIGS.find(config => config.value === category)
      return item ? item.label : category
    },
    downloadUrl(fileName) {
      return process.env.VUE_APP_BASE_API + '/common/download/resource?resource=' + encodeURIComponent(fileName)
    }
  }
}
</script>

<style scoped>
.detail-popover p {
  margin: 4px 0;
  line-height: 1.5;
}
</style>
