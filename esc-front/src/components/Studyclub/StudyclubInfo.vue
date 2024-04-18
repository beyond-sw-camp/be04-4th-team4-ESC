<script setup>
  import { reactive, ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import axios from "axios";


  const route = useRoute();
  const id = route.params.id;

  const userInfo = ref(null);
  const loaded = ref(false); 
  let loginCheck = false;
  let check = false;

  const state = reactive({
    studyclub: {},
    category: {},
    goal: {},
    exam: {}
  });

  const fetchStudyclub = async(id) => {
    try {
        const response = await fetch(`/api/studyclub/detail/${id}`);

        if(!response.ok) {
            throw new Error('response is not ok');
        }

        const data = await response.json();
        state.studyclub = data;
        console.log(state.studyclub);

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchCategory = async(id) => {
    try {
        const response = await fetch(`/api/studyclub/study-category/${id}`);

        if(!response.ok) {
            throw new Error('response is not ok');
        }

        const data = await response.json();
        state.category = data;

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchGoal = async(id) => {
    try {
        const response = await fetch(`/api/studyclub/study-goal/${id}`);

        if(!response.ok) {
            throw new Error('response is not ok');
        }

        const data = await response.json();
        state.goal = data;

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchExam = async(id) => {
    try {
          const response = await fetch(`/api/studyclub/study-exam/${id}`);

          if(!response.ok) {
              throw new Error('response is not ok');
          }

          const data = await response.json();
          state.exam = data;

          state.exam["examDate"] = splitDate(state.exam["examDate"]);
          state.studyclub["diff"] = calcDate(state.exam["examDate"]);

      } catch(error) {
          console.error('fetch error: ' + error.message);
      }
  }

  function decodeBase64(str) {
        const decoded = atob(str);
        return JSON.parse(decoded);
    }

    function fetchUserInfo(token) {
        const tokenParts = token.split('.');

    if (tokenParts.length === 3) {
        const payload = decodeBase64(tokenParts[1]);
        axios.get(`/api/user/info/${payload.sub}`)
        .then(response => {
            
            userInfo.value = response.data;
            console.log(state.studyclub.leaderId)
            console.log(userInfo.value.id)
            if( state.studyclub.leaderId == userInfo.value.id){
            check = true;
        }
        })
        .catch(error => {
            console.error('사용자 정보를 가져오는 중 오류가 발생했습니다.', error);
        })
        .finally(() => {
            loaded.value = true;
        });
        
        } else {
        console.error('잘못된 형식의 JWT 토큰입니다.');
        }
    }

  const splitDate = (date) => {

    return date.slice(0, 10);
  }

  const calcDate = (date) => {

    const currentDate = new Date(new Date().toISOString().slice(0, 10));
    return Math.trunc((new Date(date) - currentDate) / (1000 * 60 * 60 * 24));
  };
  

  onMounted(async() => {
    await fetchStudyclub(id);
    await fetchCategory(state.studyclub["id"]);
    await fetchGoal(id);
    await fetchExam(id);

    const token = localStorage.getItem('token');

        if (token) {
        fetchUserInfo(token);
        loginCheck = true;
        } else {
        console.error('토큰이 없습니다.');
        }
  })
</script>

<template>
    <div style="margin: 20px; display: flex; justify-content: space-between;">
        <div>스터디그룹 페이지</div>
        <button type="button" class="btn btn-dark" v-if="check">수정하기</button>
    </div>

    <div class="hello">👋 <{{ state.studyclub["name"] }}>에 오신 것을 환영합니다!</div>
    <div class="info">
        <div class="d-day"> {{ state.category["studyName"] }} 시험일: {{ state.exam["examDate"]}}  (D - {{ state.studyclub["diff"] }})</div>
        <div class="goal">목표 점수: {{ state.goal["score"] }}</div>
        <div class="introduce">{{ state.studyclub["introduce"] }}</div>
    </div>
</template>
    
<style scoped>
    .hello {
        text-align: center;
        font-size: 30px;
        margin-top: 40px;
        margin-bottom: 12px;
    }
    .info {
        text-align: center;
        margin-top: 10px;
    }
    .goal {
        margin: 10px;
    }
    .introduce {
        margin-top: 60px;
        margin-bottom: 60px;
        font-size: 20px;
    }
</style>