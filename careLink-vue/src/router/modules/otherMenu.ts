export default {
  path: "/other",
  redirect: "/other/index",
  meta: {
    icon: "material-symbols-light:other-admission-outline",
    // showLink: false,
    //左侧菜单
    title: "其他管理",
    rank: 7
  },
  //每一个子菜单
  children: [
    {
      path: "/activity",
      name: "活动管理",
      component: () => import("@/views/other/activity.vue"),
      meta: {
        title: "活动管理"
      }
    },
    {
      path: "/room",
      name: "房间管理",
      component: () => import("@/views/other/room.vue"),
      meta: {
        title: "房间管理"
      }
    }
  ]
} satisfies RouteConfigsTable;
