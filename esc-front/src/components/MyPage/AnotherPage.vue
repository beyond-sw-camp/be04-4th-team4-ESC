<template>
  <div class="container">
    {{ userdata.name }}님의 페이지
      <div class="userImage">
          <p>이미지</p>
      </div>
      <div class="userInfo">
        <div >
      <div class="user-evalution">
        <p>누적 신고 횟수: {{ userdata.reportCount }} </p>
        평가 테이블 추가 해야함
      </div>
    </div>
      </div>
  </div>
  
  <div class="container">

  </div>
  </template>
  
  <script setup>
    import axios from 'axios';
    import { useRouter,RouterLink } from 'vue-router';
    import { ref, onMounted } from 'vue';
  
    const userdata = ref([]);
  
    const router = useRouter();
    const memberId = router.currentRoute.value.params.id;
  

  
  onMounted(async () => {
  
  try {
    const res = await axios.get(`http://localhost:30003/user/${memberId}`)
    // 요청이 성공했을 때 받은 데이터를 Vue 컴포넌트 데이터에 저장
    userdata.value = res.data 
  
    
  } catch (error) {
    console.error('데이터를 받아오는 중 에러 발생:', error);
  }
  });
  
  
  
  </script>
  
  <style scoped>
  .container {
      display: flex;
      align-items: center;
      justify-content: center;
  }
  
  .smallPage {
      background-color: white;
      width: 180px;
      height: 210px;
      padding: 20px;
  
  }
  .bigPage {
  
      margin-right: 20px;
      margin-left: 20px;
      border: 1px solid black;
      background-color: black;
      color: white;
      font-family: '감탄로드돋움체 Bold';
      src: url('@/assets/fonts/감탄로드돋움체 Bold.ttf') format('truetype');
         
  }
  
  
  .userImage {
      background-color: #ddd;
      width: 120px;
      height: 150px;
      padding: 10px;
      margin-bottom: 20px;
      margin-right: 60px;
      margin-left: 60px;
      justify-content: center;
  }
  .userInfo {
      background-color: white;
      width: 400px;
      height: 150px;
      padding: 10px;
      margin-bottom: 20px;
      margin-right: 60px;
      margin-left: 60px;
      justify-content: center;
      font-family: '감탄로드돋움체 Bold';
      src: url('@/assets/fonts/감탄로드돋움체 Bold.ttf') format('truetype');
      border: 1px solid black;
  }
  
  </style>