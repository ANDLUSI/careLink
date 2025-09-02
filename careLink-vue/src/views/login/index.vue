<script setup lang="ts">
import Motion from "./utils/motion";
import { useRouter } from "vue-router";
import { message } from "@/utils/message";
import { loginRules } from "./utils/rule";
import { useNav } from "@/layout/hooks/useNav";
import type { FormInstance } from "element-plus";
import { useLayout } from "@/layout/hooks/useLayout";
import { useUserStoreHook } from "@/store/modules/user";
import { initRouter, getTopMenu } from "@/router/utils";
import { bg, avatar, illustration } from "./utils/static";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import { ref, reactive, toRaw, onMounted, onBeforeUnmount } from "vue";
import { useDataThemeChange } from "@/layout/hooks/useDataThemeChange";

import dayIcon from "@/assets/svg/day.svg?component";
import darkIcon from "@/assets/svg/dark.svg?component";
import Lock from "@iconify-icons/ri/lock-fill";
import User from "@iconify-icons/ri/user-3-fill";
import axios from "axios";

defineOptions({
  name: "Login"
});
const router = useRouter();
const loading = ref(false);
const ruleFormRef = ref<FormInstance>();

const { initStorage } = useLayout();
initStorage();

const { dataTheme, overallStyle, dataThemeChange } = useDataThemeChange();
dataThemeChange(overallStyle.value);
const { title } = useNav();

// const ruleForm = reactive({
//   username: "admin",
//   password: "admin123"
// });

// const onLogin = async (formEl: FormInstance | undefined) => {
//   if (!formEl) return;
//   await formEl.validate((valid, fields) => {
//     if (valid) {
//       loading.value = true;
//       useUserStoreHook()
//         .loginByUsername({ username: ruleForm.username, password: "admin123" })
//         .then(res => {
//           if (res.success) {
//             // 获取后端路由
//             return initRouter().then(() => {
//               router.push(getTopMenu(true).path).then(() => {
//                 message("登录成功", { type: "success" });
//               });
//             });
//           } else {
//             message("登录失败", { type: "error" });
//           }
//         })
//         .finally(() => (loading.value = false));
//     }
//   });
// };



const ruleForm = reactive({
  username: "",
  password: ""
});
const onLogin = async () => {

      loading.value = true;
      axios.post('http://localhost/RoleController/login', { // 发起 POST 请求到 /api/login
        phone: ruleForm.username, // 注意这里使用 ruleForm.username
        password: ruleForm.password,
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then(response => {
          if (response.data.code === 2001) { // 假设登录成功的 code 是 2001
            // 存储用户id
            sessionStorage.setItem("empId", response.data.data.employeeId);
            // 存储用户名
            sessionStorage.setItem("empName", response.data.data.name);
            //存储token
            sessionStorage.setItem("token", response.data.token);
            // 获取后端路由
            loading.value = true;
            useUserStoreHook()
              .loginByUsername({ username: ruleForm.username, password: "admin123" })
              .then(res => {
                if (res.success) {
                  // 获取后端路由
                  return initRouter().then(() => {
                    router.push(getTopMenu(true).path).then(() => {
                      message("登录成功", { type: "success" });
                    });
                  });
                } else {
                  message("登录失败", { type: "error" });
                }
              })
              .finally(() => (loading.value = false));
          } else {
            message("登录失败", { type: "error" });
          }
        })
        .catch(error => {
          console.error("登录请求失败:", error);
          message("登录请求失败", { type: "error" });
        })
        .finally(() => {
          loading.value = false;
        });
};

/** 使用公共函数，避免`removeEventListener`失效 */
function onkeypress({ code }: KeyboardEvent) {
  if (["Enter", "NumpadEnter"].includes(code)) {
    onLogin(ruleFormRef.value);
  }
}

onMounted(() => {
  window.document.addEventListener("keypress", onkeypress);
});

onBeforeUnmount(() => {
  window.document.removeEventListener("keypress", onkeypress);
});
</script>

<template>
  <div class="select-none">
    <img :src="bg" class="wave" />
    <div class="flex-c absolute right-5 top-3">
      <!-- 主题 -->
      <el-switch
        v-model="dataTheme"
        inline-prompt
        :active-icon="dayIcon"
        :inactive-icon="darkIcon"
        @change="dataThemeChange"
      />
    </div>
    <div class="login-container">
      <div class="img">
        <component :is="toRaw(illustration)" />
      </div>
      <div class="login-box">
        <div class="login-form">
          <avatar class="avatar" />
          <Motion>
            <h2 class="outline-none">{{ title }}</h2>
          </Motion>

          <el-form
            ref="ruleFormRef"
            :model="ruleForm"
            :rules="loginRules"
            size="large"
          >
            <Motion :delay="100">
              <el-form-item
                :rules="[
                  {
                    required: true,
                    message: '请输入账号',
                    trigger: 'blur'
                  }
                ]"
                prop="username"
              >
                <el-input
                  v-model="ruleForm.username"
                  clearable
                  placeholder="账号"
                  :prefix-icon="useRenderIcon(User)"
                />
              </el-form-item>
            </Motion>

            <Motion :delay="150">
              <el-form-item prop="password">
                <el-input
                  v-model="ruleForm.password"
                  clearable
                  show-password
                  placeholder="密码"
                  :prefix-icon="useRenderIcon(Lock)"
                />
              </el-form-item>
            </Motion>

            <Motion :delay="250">
              <el-button
                class="w-full mt-4"
                size="default"
                type="primary"
                :loading="loading"
                @click="onLogin(ruleFormRef)"
              >
                登录
              </el-button>
            </Motion>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("@/style/login.css");
</style>

<style lang="scss" scoped>
:deep(.el-input-group__append, .el-input-group__prepend) {
  padding: 0;
}
</style>
