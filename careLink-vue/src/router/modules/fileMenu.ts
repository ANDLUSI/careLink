export default {
  path: "/file",
  redirect: "/file/index",
  meta: {
    icon: "mi:document",
    // showLink: false,
    //左侧菜单
    title: "文档管理",
    rank: 5
  },
  //每一个子菜单
  children: [
    {
      path: "/healthRecord",
      name: "健康档案管理",
      component: () => import("@/views/file/health-record.vue"),
      meta: {
        title: "健康档案管理"
      }
    },
    {
      path: "/contract",
      name: "合同管理",
      component: () => import("@/views/file/contract.vue"),
      meta: {
        title: "合同管理"
      }
    },
    {
      path: "/dailyMonitoring",
      name: "每日监测管理",
      component: () => import("@/views/file/daily-monitoring.vue"),
      meta: {
        title: "每日监测管理"
      }
    }
  ]
} satisfies RouteConfigsTable;
