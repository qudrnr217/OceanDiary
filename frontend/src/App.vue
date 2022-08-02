<template>
  <router-view />
</template>

<script>
export default {
  name: "App",
  components: {},
};
</script>

<style>
@font-face {
  font-family: "retro";
  src: url("~@/assets/fonts/NeoDunggeunmoPro-Regular.ttf") format("truetype");
}
#app {
  font-family: retro;
}
body {
  margin: 0px;
  display: flex;
  align-items: center;
  justify-content: center;
}
h1 {
  color: white;
  font-size: 120px;
  text-align: center;
  text-shadow: 6px 2px 2px gray;
}
.button {
  width: 400px;
  height: 100px;
  background: #ffffff;
  box-shadow: 0px 10px 0px #a4b0b6;
  border-radius: 70px;
  margin: auto;
  /* button 내부 텍스트*/
  font-size: 42px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.button:hover {
  animation: flash; /* referring directly to the animation's @keyframe declaration */
  animation-duration: 1s; /* don't forget to set a duration! */
}
.text-box {
  background: #ffffff;
  box-shadow: 0px 10px 0px #a4b0b6;
  border-radius: 20px;
  margin: 0px 100px 0px 100px;
  height: 200px;
  padding: 30px 50px;
}
.background {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  margin: 0;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
.button-name {
  width: 200px;
  height: 50px;
  background: #d1d8df;
  box-shadow: 0px 10px 0px #a4b0b6;
  border-radius: 70px;
  /* button 내부 텍스트*/
  font-size: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.button-next {
  width: 70px;
  height: 30px;
  background: #d8f2f6;
  border: solid black 2px;
  border-radius: 5px;
  /* button 내부 텍스트*/
  font-size: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.button-next:hover {
  background: #b1e6ed; /*referring directly to the animation's @keyframe declaration*/
  color: white;
  border: solid white 2px;
}
.name-wrap {
  height: 25%;
  display: flex;
  align-items: center;
}
.text-wrap {
  height: 50%;
  display: flex;
  align-items: center;
  font-size: 24px;
}
.button-wrap {
  height: 25%;
  display: flex;
  align-items: center;
  justify-content: right;
}
.button-wrap {
  height: 25%;
  display: flex;
  align-items: center;
  justify-content: right;
}

.ticket {
  /* height: 114px; */
  position: relative;
  left: 85vw;
  top: 5vh;
  height: 110px;
  width: 170px;
  background-color: blue;
}
</style>
