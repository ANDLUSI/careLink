export default {
  path: "/user",
  redirect: "/user/index",
  meta: {
    icon: "material-symbols-light:account-circle-outline",
    // showLink: false,
    //左侧菜单
    title: "用户管理",
    rank: 1
  },
  //每一个子菜单
  children: [
    {
      path: "/employee",
      name: "员工管理",
      component: () => import("@/views/user/employee.vue"),
      meta: {
        title: "员工管理"
      }
    },
    {
      path: "/resident",
      name: "入住人管理",
      component: () => import("@/views/user/resident.vue"),
      meta: {
        title: "入住人管理"
      }
    },
    {
      path: "/user2",
      name: "用户管理",
      component: () => import("@/views/user/user.vue"),
      meta: {
        title: "用户管理"
      }
    },
    {
      path: "/roleM",
      name: "roleM",
      component: () => import("@/views/role/role.vue"),
      meta: {
        title: "角色管理",
        showLink :true
      }
    }
  ]
} satisfies RouteConfigsTable;
