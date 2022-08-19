<template>
  <div v-if="isSocial" id="diary-nav-member" @click="route('member')"></div>
  <div v-else id="diary-nav-single" @click="route('single')"></div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  name: "DiaryNav",
  setup() {
    const store = useStore();
    const router = useRouter();
    const isSocial = store.state.userStore.social != "NONE";
    const route = (dest) => {
      router.push({
        path: `/diary/${dest}`,
      });
    };
    return {
      isSocial,
      route,
    };
  },
};
</script>

<style scoped></style>
