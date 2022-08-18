<template>
  <div class="modal-mask box" v-if="showCallMyNameGameModal">
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">참가자들을 고르세요</div>
        <div class="modal-body">
          <div id="v-model-multiple-checkboxes">
            <template v-for="(participant, i) in participants" :key="i">
              <input
                type="checkbox"
                :id="i"
                :value="participant.participantId"
                v-model="state.checkedParticipants"
              />
              <label :for="i">{{ participant.name }}</label>
            </template>
          </div>
        </div>
        <div class="modal-footer">
          <button
            class="modal-default-button"
            @click="$emit('startCallMyNameGame', state.checkedParticipants)"
          >
            시작하기
          </button>
          <button class="modal-default-button" @click="$emit('close')">
            닫기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
export default {
  props: {
    participants: Object,
    showCallMyNameGameModal: Boolean,
  },
  emits: ["startCallMyNameGame", "close"],
  setup() {
    const state = reactive({
      checkedParticipants: [],
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

.modal-footer {
  display: flex;
  justify-content: space-between;
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
