<template>
    <div ref="chartContainer" style="width: 100%; height: 400px;"></div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import * as echarts from 'echarts';
  import axios from 'axios';
  
  const chartContainer = ref(null);
  
  const fetchData = async () => {
    try {
      const response = await axios.get('/api/chart/dashboard');
      const data = response.data.data;
  
      return {
        sales: parseFloat(data.sales),
        orders: parseInt(data.orders),
        customers: parseInt(data.customers),
        suppliers: parseInt(data.suppliers)
      };
    } catch (error) {
      console.error('Error fetching data:', error);
      return {
        sales: 0,
        orders: 0,
        customers: 0,
        suppliers: 0
      };
    }
  };
  
  const initializeChart = (data) => {
    const chart = echarts.init(chartContainer.value);
  
    const option = {
      title: {
        text: 'Dashboard Statistics',
        left: 'center',
      },
      tooltip: {
        trigger: 'item',
      },
      legend: {
        orient: 'vertical',
        left: 'left',
      },
      series: [
        {
          name: 'Statistics',
          type: 'pie',
          radius: '50%',
          data: [
            { value: data.sales, name: 'Sales' },
            { value: data.orders, name: 'Orders' },
            { value: data.customers, name: 'Customers' },
            { value: data.suppliers, name: 'Suppliers' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)',
            },
          },
        },
      ],
    };
  
    chart.setOption(option);
  };
  
  onMounted(async () => {
    const data = await fetchData();
    initializeChart(data);
  });
  </script>
  
  <style scoped>
  /* 样式可根据需要调整 */
  </style>
  