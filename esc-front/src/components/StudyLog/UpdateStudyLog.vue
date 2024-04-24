<template>
    <div class="all">
      <Header></Header>
      <div class="wrapper">
        <div class="post-header">
          <div class="title-area">
            <input type="text" v-model="editedTitle" class="title-input" />
            <div class="createdDate">{{ logData.studydate }} ~ {{ logData.enrolldate }}</div>
          </div>
          <div class="writer-area">
            <button @click="updateStudyLog">수정 완료</button>
          </div>
        </div>
        <hr>
        <div>
          <div class="section">
            <div class="section-title">🌟 스터디 내용 </div>
            <textarea v-model="editedContent" class="section-content"></textarea>
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
    const router = useRouter();
    const id = route.params.id;
    const logData = ref([]);
    const editedTitle = ref('');
    const editedContent = ref('');
  
    onMounted(async () => {
      try {
        const response = await axios.get(`http://localhost:30003/studylog/find/${id}`)
        logData.value = response.data;
        editedTitle.value = logData.value.content;
        editedContent.value = logData.value.contentInfo;
        console.log(logData.value);
      } catch (error) {
        console.error('데이터를 받아오는 중 에러 발생:', error);
      }
    });
  
    const updateStudyLog = async () => {
      try {
          const updateData = {
              id: route.params.id,
              content: editedTitle.value,
              contentInfo: editedContent.value
            };
            console.log(updateData);
        await axios.put(`http://localhost:30003/studylog/update`, updateData);
        console.log('수정 완료');
        await router.push(`/studyLog/${id}`); // 수정 완료 후 해당 스터디 로그 페이지로 이동
        
      } catch (error) {
        console.error('데이터를 업데이트하는 중 에러 발생:', error);
      }
    };
  </script>
  
  <style scoped>
    /* 스타일 코드는 동일하게 유지 */
    .title-input {
      text-align: center;
      font-size: 35px;
      margin: 10px;
      margin-bottom: 20px;
      width: 100%;
    }
  
    .section-content {
      margin: 10px;
      width: calc(100% - 20px);
      height: 300px;
    }
  </style>