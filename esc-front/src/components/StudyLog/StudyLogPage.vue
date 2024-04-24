<template>
  <div class="all">
    <Header></Header>
    <div class="wrapper">
      <div class="post-header">
        <div class="title-area">
          <div class="title">{{ logData.content }}</div>
          <div class="createdDate">
            스터디 날짜: {{ formattedStudyDate }} <br>
            작성한 날짜: {{ formattedEnrolldate }}
          </div>
        </div>
        <div class="writer-area">
          <button @click="goToEditLog">수정</button>
          <button @click="deleteLog">삭제</button>
        </div>
      </div>
      <hr>
      <div>
        <div class="section">
          <div class="section-title">🌟 스터디 내용 </div>
          <div class="section-content">{{ logData.contentInfo }}</div>
        </div>
        <hr>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script setup>
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer/Footer.vue";

const route = useRoute();
const id = route.params.id;
const logData = ref([]);
let studyclubId = ('');
let formattedStudyDate = ''; // formattedStudyDate 변수를 전역으로 선언
let formattedEnrolldate = ''; // formattedEnrolldate 변수를 전역으로 선언

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:30003/studylog/find/${id}`);
    logData.value = response.data;
    console.log(logData)
    studyclubId = logData.value.studyclubId;

    if (Array.isArray(logData.value.studydate)) {
      // startDatetime 배열에서 연, 월, 일, 시, 분, 초 정보 추출
      const [year, month, day, hour, minute, second] = logData.value.studydate;

      // 연, 월, 일, 시, 분, 초를 '-'와 ':'로 구분하여 문자열로 조합
      formattedStudyDate = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
    } else {
      formattedStudyDate = logData.value.studydate; // studydate가 배열이 아닌 경우에는 그대로 할당
    }

  // enrolldate가 있을 때만 처리
  if (!Array.isArray(logData.value.enrolldate) && logData.value.enrolldate) {
    // enrolldate가 배열이 아닌 경우에는 문자열 그대로 할당
    formattedEnrolldate = logData.value.enrolldate.split(' ')[0];
  }
  } catch (error) {
    console.error('데이터를 받아오는 중 에러 발생:', error);
  }
});

const router = useRouter();

const deleteLog = async () => {
  try {
    await axios.delete(`http://localhost:30003/studylog/delete/${id}`);
    console.log('삭제 성공');
    // 페이지를 다른 페이지로 이동시키는 코드
    await router.push(`/studyclub/${studyclubId}`);
  } catch (error) {
    console.error('데이터 삭제 중 에러 발생:', error);
  }
};

function goToEditLog() {
  router.push(`/updateStudy/${id}`);
}
</script>

<style scoped>
  .post-header {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
  }
  .wrapper {
      margin-left:12.5%;
      margin-right:12.5%;
      width:75%;
      display: grid;
  
  }
  .all {
      display: grid;
      grid-template-rows: 100px minmax(780px, auto) 200px;
      align-items: center;
      
      }
  .title {
      text-align: center;
      font-size: 35px;
      margin: 10px;
      margin-bottom: 20px;
      
  }
  .createdDate {
      color: gray;
      margin: 10px;
  }
  .writer {
      margin: 10px;
      margin-top: 55px;
  }
  .section {
      margin: 30px 20px;
      text-align: left;
  }
  .section-title {
      font-size: 20px;
      margin: 10px;
  }
  .section-subtitle {
      margin-left: 45px;
      margin-bottom: 10px;
  }
  .section-content {
      margin-left: 40px;
      margin-bottom: 20px;
  }
  .writer-area {
    height: fit-content;
    display: flex;
    justify-content: space-between;
    gap: 10px;
  }
</style>