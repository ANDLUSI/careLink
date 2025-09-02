import { dayjs, cloneDeep, getRandomIntBetween } from "./utils";
import GroupLine from "@iconify-icons/ri/group-line";
import Question from "@iconify-icons/ri/question-answer-line";
import CheckLine from "@iconify-icons/ri/chat-check-line";
import Smile from "@iconify-icons/ri/star-smile-line";
import {onMounted, ref} from "vue";
import axios from "axios";





const days = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];

/** */
const chartData = ref([
  {
    icon: GroupLine,
    bgColor: "#effaff",
    color: "#41b6ff",
    duration: 2200,
    name: "护理工人数",
    value: 0, // 初始值为 0
    // percent: "+3%",
    data: [2101, 5288, 4239, 4962, 6752, 5208, 7450]
  },
  {
    icon: Question,
    bgColor: "#fff5f4",
    color: "#e85f33",
    duration: 1600,
    name: "突发事件数量",
    value: 0, // 初始值为 0
    // percent: "+5%",
    data: [2216, 1148, 1255, 788, 4821, 1973, 4379]
  },
  {
    icon: CheckLine,
    bgColor: "#eff8f4",
    color: "#26ce83",
    duration: 1500,
    name: "入住人数量",
    value: 0, // 初始值为 0
    // percent: "+7%",
    data: [861, 1002, 3195, 1715, 3666, 2415, 3645]
  },
  {
    icon: Smile,
    bgColor: "#f6f4fe",
    color: "#7846e5",
    duration: 100,
    name: "待处理的申请",
    value: 0, // 初始值为 0
    // percent: "+50%",
    data: [861, 1002, 3195, 1715, 3666, 2415, 3645]
  }
]);

// 请求后端数据并更新 chartData（数量卡片）
export const fetchChartData = async () => {
  try {
    const responses = await Promise.all([
      axios.get("http://localhost/ChartController/nurseNum"),
      axios.get("http://localhost/ChartController/recordNum"),
      axios.get("http://localhost/ChartController/residentNum"),
      axios.get("http://localhost/ChartController/applyNum"),
    ]);

    chartData.value[0].value = responses[0].data.data; // 护理工人数
    chartData.value[1].value = responses[1].data.data; // 突发事件数量
    chartData.value[2].value = responses[2].data.data; // 入住人数量
    chartData.value[3].value = responses[3].data.data; // 待处理申请
  } catch (error) {
    console.error("获取数据失败:", error);
  }
};



// 获取年龄分布数据（饼图）
export const fetchAgeDistributionData = async () => {
  try {
    const response = await axios.get("http://localhost/ChartController/ageState");
    const data = response.data.data;

    // 将返回的对象数据转化为数组，并确保每个字段都包含 name 和 value
    const ageDistributionArray = [
      { name: "60-69岁", value: data["60-69岁"] || 0 },
      { name: "70-79岁", value: data["70-79岁"] || 0 },
      { name: "80-89岁", value: data["80-89岁"] || 0 },
      { name: "90岁以上", value: data["90岁以上"] || 0 },
      { name: "低于60岁", value: data["低于60岁"] || 0 },
    ];

    return ageDistributionArray;
  } catch (error) {
    console.error("获取年龄分布数据失败:", error);
    return [];
  }
};




/** 查询近一周的新增数量（柱状图） */
export const fetchIncidentCount = async () => {
  try {
    const response = await axios.get("http://localhost/ChartController/getIncidentCount");
    return response.data; // 返回数据
  } catch (error) {
    console.error("获取突发事件数据失败:", error);
    return {};
  }
};
/** 查询近15天的新增数量（柱状图） */
export const fetchIncidentCount15 = async () => {
  try {
    const response = await axios.get("http://localhost/ChartController/getIncidentCount15");
    return response.data; // 返回数据
  } catch (error) {
    console.error("获取突发事件数据失败:", error);
    return {};
  }
};





export { chartData,  };
