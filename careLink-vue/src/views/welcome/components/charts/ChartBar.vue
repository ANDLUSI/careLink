<script setup lang="ts">
import { useDark, useECharts } from "@pureadmin/utils";
import { type PropType, ref, computed, watch, nextTick, onMounted } from "vue";
import { fetchIncidentCount } from "@/views/welcome/data";

const props = defineProps({
  requireData: {
    type: Array as PropType<Array<number>>,
    default: () => []
  },
  questionData: {
    type: Array as PropType<Array<number>>,
    default: () => []
  }
});

const { isDark } = useDark();

const theme = computed(() => (isDark.value ? "dark" : "light"));

const chartRef = ref();
const { setOptions } = useECharts(chartRef, {
  theme
});

const tag = ref([]);

// 在组件挂载时调用 获取数据方法
onMounted(async () => {

  // 条形图
  const incidentCount = await fetchIncidentCount();
  tag.value = incidentCount;

  // 确保 DOM 更新完成后再设置图表选项
  await nextTick();
  setOptions(getChartOptions());
});

const formatTimestampToDate = (timestamp: number): string => {
  const date = new Date(timestamp);
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始，需要加1
  const day = String(date.getDate()).padStart(2, '0');
  return `${month}月${day}日`;
};

const getChartOptions = () => ({
  container: ".bar-card",
  color: ["#41b6ff", "#e85f33"],
  tooltip: {
    trigger: "axis",
    axisPointer: {
      type: "none"
    }
  },
  grid: {
    top: "20px",
    left: "50px",
    right: 0
  },
  legend: {
    data: ["新增突发事件"],
    textStyle: {
      color: "#606266",
      fontSize: "0.875rem"
    },
    bottom: 0
  },
  xAxis: [
    {
      //底部文字
      type: "category",
      data: tag.value.map(item => formatTimestampToDate(item.date)),
      axisLabel: {
        fontSize: "0.875rem"
      },
      axisPointer: {
        type: "shadow"
      }
    }
  ],
  yAxis: [
    {
      type: "value",
      axisLabel: {
        fontSize: "0.875rem"
      },
      splitLine: {
        show: false // 去网格线
      }
      // name: "单位: 个"
    }
  ],
  series: [
    {
      name: "新增突发事件",
      type: "bar",
      barWidth: 10,
      itemStyle: {
        color: "#e86033ce",
        borderRadius: [10, 10, 0, 0]
      },
      data: props.questionData
    }
  ]
});

watch(
  () => props,
  async () => {
    await nextTick(); // 确保 DOM 更新完成后再执行
    setOptions(getChartOptions());
  },
  {
    deep: true,
    immediate: true
  }
);
</script>

<template>
  <div ref="chartRef" style="width: 100%; height: 365px" />
</template>
