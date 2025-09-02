export default {
  path: "/nurser",
  redirect: "/nurser/index",
  meta: {
    icon: "ri:nurse-line",
    // showLink: false,
    //左侧菜单
    title: "护理管理",
    rank: 2
  },
  //每一个子菜单
  children: [
    {
      path: "/event",
      name: "突发事件管理",
      component: () => import("@/views/nurse/health-record.vue"),
      meta: {
        title: "突发事件管理",
        showParent: true
      }
    },
    {
      path: "/manNurse",
      name: "护理人员管理",
      component: () => import("@/views/nurse/nurse.vue"),
      meta: {
        title: "护理人员管理",
        showParent: true
      }
    },
    {
      path: "/plan",
      name: "护理计划管理",
      component: () => import("@/views/nurse/nurse-plane.vue"),
      meta: {
        title: "护理计划管理",
        showParent: true
      }
    }
  ]
} satisfies RouteConfigsTable;
