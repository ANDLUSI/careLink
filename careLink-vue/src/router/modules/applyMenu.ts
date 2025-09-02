export default {
  path: "/apply",
  redirect: "/apply/index",
  meta: {
    icon: "solar:document-text-linear",
    // showLink: false,
    //左侧菜单
    title: "申请管理",
    rank: 3
  },
  //每一个子菜单
  children: [
    {
      path: "/out",
      name: "退宿申请审核",
      component: () => import("@/views/apply/out-apply.vue"),
      meta: {
        title: "退宿申请审核",
        showParent: true
      }
    },
    {
      path: "/sign",
      name: "活动报名审核",
      component: () => import("@/views/apply/sign-up.vue"),
      meta: {
        title: "活动报名审核",
        showParent: true
      }
    }
  ]
} satisfies RouteConfigsTable;
