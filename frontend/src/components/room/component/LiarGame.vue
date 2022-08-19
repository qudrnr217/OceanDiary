<template>
  <div class="modal-mask box">
    <div class="modal-wrapper">
      <div class="modal-container" v-if="!categoryChosen">
        <div class="modal-header">카테고리를 고르세요</div>
        <div class="modal-body">
          <div
            class="button-metro category-item"
            v-for="(category, i) in state.liarGameKeywords.categories"
            :key="i"
            @click="$emit('decideCategory', category)"
          >
            {{ category }}
          </div>
        </div>
        <div class="modal-footer">
          <button class="modal-default-button" @click="$emit('close')">
            닫기
          </button>
        </div>
      </div>
      <div class="modal-container" v-if="categoryChosen">
        <div class="modal-header">
          <slot name="header"> default header </slot>
        </div>

        <div class="modal-body">
          <slot name="body"> default body </slot>
        </div>

        <div class="modal-footer">
          <slot name="footer">
            <button class="modal-default-button" @click="$emit('close')">
              닫기
            </button>
          </slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { liarGameKeywords } from "@/const/const.js";
import { reactive } from "vue";
export default {
  props: {
    categoryChosen: Boolean,
  },
  emits: ["decideCategory", "close"],
  setup() {
    const state = reactive({
      liarGameKeywords: liarGameKeywords,
    });
    return { state };
  },
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 50vh;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin: 20px 0;
}

.category-item {
  margin: 3px;
}

.modal-default-button {
  display: block;
  margin-top: 1rem;
}

.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.5s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}
</style>
