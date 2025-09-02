<script setup lang="ts">
import {computed, onMounted, ref, watch} from "vue";
import ReCol from "@/components/ReCol";
import {useDark} from "./utils";
import {ReNormalCountTo} from "@/components/ReCountTo";
import {ChartBar, ChartLine, ChartRound} from "./components/charts";
import Segmented, {type OptionsType} from "@/components/ReSegmented";
import {chartData, fetchChartData, fetchAgeDistributionData, fetchIncidentCount, fetchIncidentCount15} from "./data";
import VChart from "vue-echarts";
import { use } from "echarts/core";
import { PieChart } from "echarts/charts";
import { TitleComponent, TooltipComponent, LegendComponent } from "echarts/components";
import { CanvasRenderer } from "echarts/renderers";

use([PieChart, TitleComponent, TooltipComponent, LegendComponent, CanvasRenderer]);


defineOptions({
  name: "Welcome"
});

const { isDark } = useDark();

let curWeek = ref(1); // 0上周、1本周
const optionsBasis: Array<OptionsType> = [
  // {
  //   label: "近15天"
  // },
  {
    label: "近7天"
  }
];

/** 分析概览（柱状图） */
const barChartData = computed(() => {
  return [
    {
      // 15天
      questionData: incidentCountA15.value.map(item => item.count)
    },
    {
      // 7天
      questionData: incidentCountA.value.map(item => item.count)
    }
  ];
});

// 创建响应式的 `ageDistributionData`
const ageDistributionData = ref([
  { name: "60-69岁", value: 0 },
  { name: "70-79岁", value: 0 },
  { name: "80-89岁", value: 0 },
  { name: "90岁以上", value: 0 },
  { name: "低于60岁", value: 0 },
]);



// 饼图选项
const pieChartOption = ref({
  title: { text: "老人年龄分布", left: "140px" },
  tooltip: { trigger: "item" },
  legend: { orient: "vertical", left: "left" },
  series: [
    {
      name: "年龄分布",
      type: "pie",
      radius: "50%",
      data: ageDistributionData.value,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: "rgba(0, 0, 0, 0.5)",
        },
      },
    },
  ],
});

//创建响应式数组
const incidentCountA = ref([]);
const incidentCountA15 = ref([]);
// 在组件挂载时调用 获取数据方法
onMounted(async () => {
  //卡片数据
  fetchChartData();
  //饼图
  ageDistributionData.value = await fetchAgeDistributionData();
  //条形图
  incidentCountA.value = await fetchIncidentCount();
  incidentCountA15.value = await fetchIncidentCount15();

});

// 监听 ageDistributionData 的变化，动态更新图表数据
watch(ageDistributionData, (newData) => {
  pieChartOption.value.series[0].data = newData;
});
</script>

<template>
  <div>
    <el-row :gutter="24" justify="space-around">
      <re-col
        v-for="(item, index) in chartData"
        :key="index"
        v-motion
        class="mb-[18px]"
        :value="6"
        :md="12"
        :sm="12"
        :xs="24"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 80 * (index + 1)
          }
        }"
      >
        <el-card class="line-card" shadow="never">
          <div class="flex justify-between">
            <span class="text-md font-medium">
              {{ item.name }}
            </span>
            <div
              class="w-8 h-8 flex justify-center items-center rounded-md"
              :style="{
                backgroundColor: isDark ? 'transparent' : item.bgColor
              }"
            >
              <IconifyIconOffline
                :icon="item.icon"
                :color="item.color"
                width="18"
              />
            </div>
          </div>
          <div class="flex justify-between items-start mt-3">
            <div class="w-1/2">
              <ReNormalCountTo
                :duration="item.duration"
                :fontSize="'1.6em'"
                :startVal="100"
                :endVal="item.value"
              />
              <p class="font-medium text-green-500">{{ item.percent }}</p>
            </div>
            <ChartLine
              v-if="item.data.length > 1"
              class="!w-1/2"
              :color="item.color"
              :data="item.data"
            />
            <ChartRound v-else class="!w-1/2" />
          </div>
        </el-card>
      </re-col>

      <re-col
        v-motion
        class="mb-[18px]"
        :value="18"
        :xs="24"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 400
          }
        }"
      >
        <el-card class="bar-card" shadow="never">
          <div class="flex justify-between">
            <span class="text-md font-medium">突发事件情况</span>
            <Segmented v-model="curWeek" :options="optionsBasis" />
          </div>
          <div class="flex justify-between items-start mt-3">
            <ChartBar
              :questionData="barChartData[curWeek].questionData"
            />
          </div>
        </el-card>
      </re-col>

      <!--饼图-->
      <re-col
        v-motion
        class="mb-[18px]"
        :value="6"
        :xs="24"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 640
          }
        }"
      >
        <el-card shadow="never" class="h-[450px]">
          <VChart :option="pieChartOption" autoresize style="width: 100%; height: 500%;"></VChart>
        </el-card>

      </re-col>
    </el-row>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-card) {
  --el-card-border-color: none;

  /* 解决概率进度条宽度 */
  .el-progress--line {
    width: 85%;
  }

  /* 解决概率进度条字体大小 */
  .el-progress-bar__innerText {
    font-size: 15px;
  }

  /* 隐藏 el-scrollbar 滚动条 */
  .el-scrollbar__bar {
    display: none;
  }

  /* el-timeline 每一项上下、左右边距 */
  .el-timeline-item {
    margin: 0 6px;
  }
}

.main-content {
  margin: 20px 20px 0 !important;
}
</style>
