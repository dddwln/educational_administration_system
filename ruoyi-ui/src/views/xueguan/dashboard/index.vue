<template>
  <div class="dashboard-screen">
    <div class="screen-header">
      <div>
        <h2>学管数据大屏</h2>
        <p>材料归档、审核流转、学院分布和时间趋势</p>
      </div>
      <div class="filters">
        <el-select v-model="query.ownerRole" placeholder="全部角色" clearable size="small" @change="loadData">
          <el-option label="教师" value="teacher" />
          <el-option label="学生" value="student" />
        </el-select>
        <el-date-picker v-model="dateRange" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" size="small" @change="loadData" />
      </div>
    </div>

    <el-row :gutter="16" class="metric-row">
      <el-col :span="6"><div class="metric"><span>上传总量</span><b>{{ summary.materialCount || 0 }}</b></div></el-col>
      <el-col :span="6"><div class="metric"><span>待审核</span><b>{{ summary.pendingCount || 0 }}</b></div></el-col>
      <el-col :span="6"><div class="metric"><span>审核通过</span><b>{{ summary.approvedCount || 0 }}</b></div></el-col>
      <el-col :span="6"><div class="metric"><span>金额类合计</span><b>{{ amountText }}</b></div></el-col>
    </el-row>

    <el-row :gutter="16" class="chart-row">
      <el-col :span="12"><div ref="categoryChart" class="chart"></div></el-col>
      <el-col :span="12"><div ref="statusChart" class="chart"></div></el-col>
    </el-row>
    <el-row :gutter="16">
      <el-col :span="12"><div ref="deptChart" class="chart"></div></el-col>
      <el-col :span="12"><div ref="trendChart" class="chart"></div></el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { getDashboard } from '@/api/xueguan/dashboard'

const CATEGORY_LABELS = {
  competition: '竞赛材料',
  research_award: '科研奖励材料',
  research_project: '科研项目材料',
  faculty_info: '教职工信息材料',
  education_history: '学习经历材料',
  personal_honor: '个人荣誉称号材料',
  entrepreneurship: '个人创业情况材料',
  part_time: '个人兼职情况材料',
  new_teacher: '新入职教师记录材料',
  distinguished_teacher: '特聘教师记录材料',
  public_institution_assessment: '历年事业单位考核结果材料',
  maternal_child: '妇幼信息材料'
}

export default {
  name: 'XgDashboard',
  data() {
    return {
      query: {},
      dateRange: [],
      summary: {},
      charts: []
    }
  },
  computed: {
    amountText() {
      const amount = Number(this.summary.totalAmount || 0)
      return amount.toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })
    }
  },
  mounted() {
    this.loadData()
    window.addEventListener('resize', this.resizeCharts)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts)
    this.charts.forEach(chart => chart.dispose())
  },
  methods: {
    loadData() {
      const params = Object.assign({}, this.query)
      if (this.dateRange && this.dateRange.length === 2) {
        params.beginTime = this.dateRange[0]
        params.endTime = this.dateRange[1]
      }
      getDashboard(params).then(res => {
        const data = res.data || {}
        this.summary = data.summary || {}
        this.renderCharts(data)
      })
    },
    renderCharts(data) {
      this.charts.forEach(chart => chart.dispose())
      this.charts = [
        echarts.init(this.$refs.categoryChart),
        echarts.init(this.$refs.statusChart),
        echarts.init(this.$refs.deptChart),
        echarts.init(this.$refs.trendChart)
      ]

      const categoryStats = (data.categoryStats || []).map(item => ({
        name: CATEGORY_LABELS[item.name] || item.name,
        value: item.value
      }))
      const statusStats = data.auditStatusStats || []
      const deptStats = data.deptStats || []
      const trendStats = data.trendStats || []

      this.charts[0].setOption({
        title: { text: '材料分类分布', textStyle: { color: '#fff' } },
        tooltip: { trigger: 'item' },
        legend: { type: 'scroll', bottom: 0, textStyle: { color: '#dbeafe' } },
        series: [{ type: 'pie', radius: ['36%', '62%'], center: ['50%', '45%'], data: categoryStats }]
      })
      this.charts[1].setOption({
        title: { text: '审核状态', textStyle: { color: '#fff' } },
        tooltip: { trigger: 'item' },
        legend: { bottom: 0, textStyle: { color: '#dbeafe' } },
        series: [{ type: 'pie', radius: '60%', center: ['50%', '45%'], data: statusStats }]
      })
      this.charts[2].setOption({
        title: { text: '学院/部门上传量', textStyle: { color: '#fff' } },
        tooltip: {},
        grid: { left: 50, right: 24, bottom: 80 },
        xAxis: { type: 'category', data: deptStats.map(item => item.name), axisLabel: { color: '#dbeafe', rotate: 30 } },
        yAxis: { type: 'value', axisLabel: { color: '#dbeafe' } },
        series: [{ type: 'bar', data: deptStats.map(item => item.value), itemStyle: { color: '#2dd4bf' } }]
      })
      this.charts[3].setOption({
        title: { text: '月度上传趋势', textStyle: { color: '#fff' } },
        tooltip: { trigger: 'axis' },
        grid: { left: 50, right: 24, bottom: 50 },
        xAxis: { type: 'category', data: trendStats.map(item => item.name), axisLabel: { color: '#dbeafe' } },
        yAxis: { type: 'value', axisLabel: { color: '#dbeafe' } },
        series: [{ type: 'line', smooth: true, areaStyle: {}, data: trendStats.map(item => item.value), itemStyle: { color: '#60a5fa' } }]
      })
    },
    resizeCharts() {
      this.charts.forEach(chart => chart.resize())
    }
  }
}
</script>

<style scoped>
.dashboard-screen {
  min-height: calc(100vh - 84px);
  padding: 20px;
  color: #fff;
  background: #111827;
}
.screen-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 18px;
}
.screen-header h2 {
  margin: 0 0 6px;
  font-size: 28px;
}
.screen-header p {
  margin: 0;
  color: #cbd5e1;
}
.filters {
  display: flex;
  gap: 10px;
}
.metric-row,
.chart-row {
  margin-bottom: 16px;
}
.metric {
  height: 104px;
  padding: 18px;
  border: 1px solid #334155;
  border-radius: 8px;
  background: #1f2937;
}
.metric span {
  display: block;
  color: #cbd5e1;
}
.metric b {
  display: block;
  margin-top: 10px;
  font-size: 30px;
}
.chart {
  height: 360px;
  padding: 16px;
  border: 1px solid #334155;
  border-radius: 8px;
  background: #1f2937;
}
</style>
