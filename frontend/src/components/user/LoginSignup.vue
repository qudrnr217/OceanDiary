<template>
  <div class="box main-box">
    <div class="guide-wrap">
      <vue-writer
        :array="[`정보를 입력한 후, '발급' 버튼을 눌러주세요.`]"
        :typeSpeed="70"
        :iterations="1"
      ></vue-writer>
    </div>
    <div style="display: flex; width: 100%; height: 70%">
      <div
        style="
          width: 40%;
          display: flex;
          align-items: center;
          justify-content: center;
        "
      >
        <img src="@/assets/아이콘/[아이콘]정기권_상단.png" style="width: 80%" />
      </div>
      <div style="width: 60%; display: flex; align-items: center">
        <table class="signup-form">
          <tr>
            <th>이름 :</th>
            <td>
              <input
                type="text"
                v-model="form.userinfo.name"
                class="signup-input"
                @keydown="typewriter"
              />
            </td>
          </tr>
          <tr>
            <th>이메일 :</th>
            <td>
              <input
                type="email"
                v-model="form.userinfo.email"
                class="signup-input"
                @keydown="typewriter"
              />
            </td>
          </tr>
          <tr>
            <th>생년월일 :</th>
            <td>
              <div style="display: flex; align-items: center">
                <input
                  class="signup-input"
                  @keydown="typewriter"
                  v-model="form.date.year"
                />
                년
                <input
                  class="signup-input"
                  @keydown="typewriter"
                  v-model="form.date.month"
                />
                월
                <input
                  class="signup-input"
                  @keydown="typewriter"
                  v-model="form.date.day"
                />
                일
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="button-wrap">
      <div
        class="button-metro"
        @click="submit(), selectSound()"
        style="float: right"
      >
        발 급
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { reactive } from "vue";
import { signup } from "@/api/login.js";
import useSound from "vue-use-sound";
import selectSfx from "@/assets/Select.wav";
import buttonSfx from "@/assets/Typewriter.wav";
export default {
  setup() {
    const [selectSound] = useSound(selectSfx);
    const [typewriter] = useSound(buttonSfx);

    const router = useRouter();
    const store = useStore();
    const form = reactive({
      userinfo: {
        email: "",
        name: "",
        birth: new Date(),
        oauthId: "",
      },
      date: {
        year: "",
        month: "",
        day: "",
      },
    });
    /*
    (TODO) 요청 시 oauthID 전달, accessToken, refreshToken 관련
    */
    const urlParams = new URL(location.href).searchParams;
    const social = urlParams.get("social");

    var submit = () => {
      // 회원가입 요청에 필요한 birth, oauthId를 형식에 맞게 준비한다.
      form.userinfo.birth.setFullYear(form.date.year);
      form.userinfo.birth.setMonth(form.date.month);
      form.userinfo.birth.setDate(form.date.day);
      form.userinfo.oauthId = store.state.userStore.oauthId;
      // 회원가입 요청을 보낸다.
      signup(
        social,
        form.userinfo,
        (response) => {
          console.log("회원가입 요청 : 성공!");
          // vuex에 회원 정보 업데이트
          store.commit("userStore/SET_NAME", response.data.name);
          store.commit("userStore/SET_TOKEN", response.data.accessToken);
          store.commit("userStore/SET_USERID", response.data.userId);
          router.push({
            name: "station-chat",
            params: { nextLink: "map", speech: "어디로 가시나요?" },
          });
        },
        (error) => {
          console.log(error);
        }
      );
    };
    /* (TODO) 로그인 사항에 대한 예외처리를 구현합니다.
    const checkInput = () => {

    }
    */
    return {
      form,
      submit,
      selectSound,
      typewriter,
    };
  },
};
</script>

<style>
.button-wrap {
  height: 15%;
  padding-right: 30px;
}
.ticket-img {
  width: 30%;
  height: auto;
  position: relative;
  top: 8%;
  left: 12%;
}
.signup-input {
  border: solid 2px #d1d8df;
  border-radius: 10px;
  width: 200px;
  height: 50px;
  font-size: 30px;
  font-family: "retro";
}
.signup-form {
  width: 90%;
  text-align: left;
  font-size: 30px;
}
.signup-form tr {
  padding-bottom: 20px;
}
.signup-form th {
  width: 150px;
  padding-right: 20px;
  text-align: right;
}
.signup-form .signup-input {
  width: 100%;
}
</style>
