<template>
  <div id="home">
    <div class="charts-container">
      <!-- <OrderCustomerSupplier class="chart" /> -->
      <MonthlyTotalOrderPrice class="chart" />
    </div>
  </div>
</template>

<script setup>
import MonthlyTotalOrderPrice from '@/views/home/charts/MonthlyTotalOrderPrice.vue';
// import OrderCustomerSupplier from '@/views/home/charts/OrderCustomerSupplier.vue';
</script>

<style>
#home {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  /* 其他样式 */
}

.charts-container {
  display: flex;
  justify-content: space-between; /* 水平分布 */
  gap: 20px; /* 组件之间的间距 */
  padding: 20px; /* 可选，添加内边距以适应布局 */
}

.chart {
  flex: 1; /* 使每个图表占据相等的空间 */
}
</style>
