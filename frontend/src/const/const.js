/*
 * index   장소
 *  0      바다
 *  1      도서관
 *  2      카페
 *  3      축제
 *  4      집
 *
 * names : 장소 이름
 * explains : 장소 설명
 * images : 장소 사진
 * links : 장소 url 영문
 * stations : 장소 지하철 역 풀네임
 */
const names = ["바다", "도서관", "카페", "축제", "집"];
const links = ["ocean", "library", "cafe", "festival", "house"];
const indexes = {
  ocean: 0,
  library: 1,
  cafe: 2,
  festival: 3,
  house: 4,
};
const types = {
  ocean: "스터디",
  library: "스터디",
  cafe: "스터디",
  festival: "게임",
  house: "스터디",
};
const explains = [
  "잔잔한 파도 소리를 들으며, 편안하게 집중할 수 있습니다.",
  "도서관에서 소음 없이, 조용히 집중할 수 있습니다.",
  "편안한 분위기에서 함께 이야기하며 공부할 수 있습니다.",
  "지루하거나, 활력이 필요할 땐 축제에서 게임을 즐기세요.",
  "집에 준비된 다양한 기능을 사용할 수 있습니다.",
];
const stations = [
  "바닷가 앞",
  "숲 속 도서관",
  "카페",
  "여름 불꽃 축제",
  "한적한 주택가",
];
const arriveImages = [
  "[05_도착]바다.png",
  "[05_도착]도서관.png",
  "[05_도착]카페.png",
  "[05_도착]축제.png",
  "[05_도착]집.png",
];
const arriveOtterImages = [
  "[05_도착]바다_해달.png",
  "[05_도착]도서관_해달.png",
  "[05_도착]카페_해달.png",
  "[05_도착]축제_해달.png",
  "[05_도착]집_해달.png",
];
const roomImages = [
  "[06_방목록]바다.png",
  "[06_방목록]도서관.png",
  "[06_방목록]카페.png",
  "[06_방목록]축제.png",
  "[06_방목록]집.png",
];
const roomOtterImages = [
  "[06_방목록]바다_해달.png",
  "[06_방목록]도서관_해달.png",
  "[06_방목록]카페_해달.png",
  "[06_방목록]축제_해달.png",
  "[06_방목록]집_해달.png",
];
const jobs = ["안전요원", "사서", "바리스타", "MC해달", "집달이"];
const greetings = [
  "바다 테마에서는 잔잔한 파도 소리와 함께 물멍을 즐길 수 있어요!",
  "도서관에서는 쉿! 조용히 책 넘기는 소리만 들리는 공간이에요!",
  "친구들과 도란도란 이야기를 나누고 싶다면, 여유로운 카페 분위기를 즐기며 함께 시간을 보낼 수 있어요!",
  "친구들과 함께 할 때 가장 재미있는 바닷마을 축제! 지치거나 활력이 필요할 때 게임을 즐길 수 있어요!",
  "친구와 단둘이 또는 혼자 편하게 있고 싶다면? ASMR 없이 고요한 집에서 영상통화처럼 즐겨보세요!",
];
const icons = [
  "[아이콘]카테고리_바다.png",
  "[아이콘]카테고리_도서관.png",
  "[아이콘]카테고리_카페.png",
  "[아이콘]카테고리_축제.png",
  "[아이콘]카테고리_집.png",
];
const themeColors = [
  ["#4467c2", "#b1e6ed"],
  ["#5d7510", "#aabc61"],
  ["#cf833e", "#f7d794"],
  ["#9d438e", "#d879ce"],
  ["#a37e71", "#e3d7d8"],
];
export {
  names,
  indexes,
  types,
  explains,
  arriveImages,
  arriveOtterImages,
  roomImages,
  roomOtterImages,
  links,
  stations,
  icons,
  themeColors,
  jobs,
  greetings,
};
