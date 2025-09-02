export default {
  path: "/charge",
  redirect: "/charge/index",
  meta: {
    icon: "hugeicons:money-bag-02",
    // showLink: false,
    //左侧菜单
    title: "收费管理",
    rank: 6
  },
  //每一个子菜单
  children: [
    {
      path: "/chargeStandard",
      name: "收费标准管理",
      component: () => import("@/views/charge/charge-standard.vue"),
      meta: {
        title: "收费标准管理"
      }
    },
    {
      path: "/bill",
      name: "账单管理",
      component: () => import("@/views/charge/bill.vue"),
      meta: {
        title: "账单管理"
      }
    }
  ]
} satisfies RouteConfigsTable;
