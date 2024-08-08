<template>
    <div ref="chartContainer" class="chart-container"></div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue';
  import * as echarts from 'echarts/core';
  import { GridComponent, TitleComponent, TooltipComponent } from 'echarts/components';
  import { LineChart } from 'echarts/charts';
  import { UniversalTransition } from 'echarts/features';
  import { CanvasRenderer } from 'echarts/renderers';
  
  // 使用 ECharts 组件
  echarts.use([GridComponent, LineChart, CanvasRenderer, UniversalTransition, TitleComponent, TooltipComponent]);
  
  const chartContainer = ref(null);
  
  onMounted(async () => {
    const chartDom = chartContainer.value;
    const myChart = echarts.init(chartDom);
    // const myChart = echarts.init(chartDom, 'dark');//暗黑模式
  
    // 获取数据
    try {
      const response = await fetch('/api/chart/monthly');
      const result = await response.json();
      
      if (result.code === 200) {
        const { months, monthlyOrderTotals } = result.data;
  
        // 转换订单总价为数值类型
        const orderTotals = monthlyOrderTotals.map(total => parseFloat(total));
  
        const option = {
          title: {
            text: '每月订单总价',
            left: 'center',
            top: 20
          },
          xAxis: {
            type: 'category',
            data: months
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: orderTotals,
              type: 'line',
              smooth: true // Optional: makes the line smooth
            }
          ],
          tooltip: {
            trigger: 'axis'
          }
        };
  
        myChart.setOption(option);
      } else {
        console.error('Failed to fetch data:', result.message);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  
    // Resize chart on window resize
    window.addEventListener('resize', () => myChart.resize());
  });
  </script>
  
  <style scoped>
  .chart-container {
    width: 100%;
    height: 400px;
  }
  </style>
  