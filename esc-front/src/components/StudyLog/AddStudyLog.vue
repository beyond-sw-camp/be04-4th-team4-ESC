<template>
  <div class="all">
    <Header></Header>
    <div class="wrapper">
      <div>
        <h1>로그 생성</h1>
        <form @submit.prevent="createLog">
          <div class="box">
            <label for="title">제목:</label>
            <input type="text" id="title" v-model="logTitle">
          </div>
          <div class="box">
            <label for="content">내용:</label>
            <textarea id="content" v-model="logContent"></textarea>
          </div>
          <div class="box">
            <label for="studyDate">스터디 일정:</label>
            <select id="studyDate" v-model="selectedSchedule">
              <option v-for="schedule in schedules" :key="schedule.id" :value="schedule.id">
                {{ formatStartDate(schedule.startDatetime) }}
              </option>
            </select>
          </div>
          <button type="submit">로그 생성</button>
        </form>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer/Footer.vue";

const router = useRouter();
const studyclubId = router.currentRoute.value.params.id;
const logTitle = ref('');
const logContent = ref('');
const selectedSchedule = ref(null);
const schedules = ref([]);

async function createLog() {
  try {
    const selectedScheduleId = selectedSchedule.value; // 선택한 일정의 ID 값을 가져옴
    console.log(selectedScheduleId)
    await axios.post(`http://localhost:30003/studylog/insert`, {
      studyclubId: studyclubId,
      content: logTitle.value,
      contentInfo: logContent.value,
      enrolldate: '', 
      scheduleId: selectedSchedule.value,
    });
    router.go(-1);
  } catch (error) {
    console.error('로그 생성 중 에러 발생:', error);
  }
}

function formatStartDate(startDatetime) {
  const [year, month, day] = startDatetime;
  return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
}

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:30003/study-schedule/studyclub/${studyclubId}`);
    schedules.value = response.data.studySchedules;
  } catch (error) {
    console.error('스터디 일정을 불러오는 중 에러 발생:', error);
  }
});
</script>

<style scoped>
#title, #content {
  width: 100%;
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}
.box {
  margin-bottom: 20px;
}
.wrapper {
  margin-left: 12.5%;
  margin-right: 12.5%;
  width: 75%;
  display: grid;
}
.all {
  display: grid;
  grid-template-rows: 100px minmax(780px, auto) 200px;
  align-items: center;
}
</style>
