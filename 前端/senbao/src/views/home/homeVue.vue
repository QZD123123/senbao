<template>
    <div id="home">
      <!-- 可以放置多个组件，这里以 MonthlyTotalOrderPrice 为例 -->
      <MonthlyTotalOrderPrice />
      <!-- 其他组件 -->
      <!-- <OtherComponent /> -->
      <!-- 继续添加其他组件 -->
    </div>
  </template>
  
  <script setup>
  import MonthlyTotalOrderPrice from '@/views/home/charts/MonthlyTotalOrderPrice.vue';
  // 引入其他组件
//   import OtherComponent from '@/views/home/charts/OtherComponent.vue'; // 示例：引入其他组件
  </script>
  
  <style>
  #home {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
    /* 其他样式 */
  }
  </style>
  