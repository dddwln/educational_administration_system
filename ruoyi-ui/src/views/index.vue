<template>
  <div class="app-container xg-home">
    <div class="home-header">
      <div>
        <h1>学管平台工作台</h1>
        <p>围绕材料上传、审核归档和数据分析开展日常管理。</p>
      </div>
      <el-button type="primary" icon="el-icon-data-analysis" @click="$router.push('/xueguan/dashboard')">查看数据大屏</el-button>
    </div>

    <el-row :gutter="16">
      <el-col :span="8">
        <div class="entry-card" @click="$router.push('/xueguan/material')">
          <i class="el-icon-upload"></i>
          <h3>材料上传</h3>
          <p>学生和教师按分类提交竞赛、科研、人事、荣誉等材料。</p>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="entry-card" @click="$router.push({ path: '/xueguan/audit', query: { audit: '1' } })">
          <i class="el-icon-finished"></i>
          <h3>材料审核</h3>
          <p>管理员审核待办材料，通过后进入归档统计。</p>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="entry-card" @click="$router.push('/xueguan/dashboard')">
          <i class="el-icon-s-data"></i>
          <h3>数据大屏</h3>
          <p>查看分类分布、审核状态、部门上传量和月度趋势。</p>
        </div>
      </el-col>
    </el-row>

    <el-alert
      class="home-note"
      title="角色说明"
      type="info"
      description="教师和学生只能查看并维护自己上传的材料；管理员可查看全部材料并执行审核。"
      show-icon
      :closable="false"
    />
  </div>
</template>

<script>
export default {
  name: 'Index'
}
</script>

<style scoped>
.xg-home {
  min-height: calc(100vh - 84px);
  background: #f5f7fb;
}
.home-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 18px;
  padding: 22px 24px;
  border-radius: 8px;
  background: #fff;
}
.home-header h1 {
  margin: 0 0 8px;
  font-size: 26px;
  color: #1f2937;
}
.home-header p {
  margin: 0;
  color: #606266;
}
.entry-card {
  min-height: 180px;
  padding: 24px;
  border-radius: 8px;
  background: #fff;
  cursor: pointer;
  transition: transform 0.15s ease, box-shadow 0.15s ease;
}
.entry-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 22px rgba(31, 41, 55, 0.08);
}
.entry-card i {
  font-size: 34px;
  color: #2563eb;
}
.entry-card h3 {
  margin: 16px 0 10px;
  font-size: 20px;
  color: #1f2937;
}
.entry-card p {
  margin: 0;
  line-height: 1.7;
  color: #606266;
}
.home-note {
  margin-top: 18px;
}
</style>
