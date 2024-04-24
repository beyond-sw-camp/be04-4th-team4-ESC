<template>
  <div class="all">
    <Header></Header>
    <div class="wrapper">
      <div>
        <div style="margin: 20px; margin-bottom: 40px;">{{ state.userInfo["nickname"] }}님의 마이페이지</div>
        <div class="profile">
          <img src="@/assets/profile.png">
        </div>
        <div class="user-info">
          <div class="nickname">{{ state.userInfo["nickname"] }}</div>
          <div>포인트: {{ state.userInfo["point"] }}</div>
          <div>레벨: {{ state.userInfo["grade"] }}</div>
        </div>
      </div>

      <div class="container">
          <div class="box">
              <p class="box-title">참여 중인 스터디</p>
              <div class="box-content">
                <ul class="list-group list-group-flush">
                  <UserProps v-for="studyclub in state.userStudyclubs" :value="studyclub.name" :studyclub="studyclub"></UserProps>
                </ul>
              </div>
          </div>
          <div class="box">
              <p class="box-title">작성한 로그</p>
              <div class="box-content">
                <ul class="list-group list-group-flush">
                  <UserProps v-for="log in state.userLogs" :value="log" :log="log"></UserProps>
                </ul>
              </div>
          </div>
          <div class="box">
              <p class="box-title">참여 신청한 모집글</p>
              <div class="box-content">
                <ul class="list-group list-group-flush">
                  <UserProps v-for="application in state.userApplications" :value="application.id" :application="application"></UserProps>
                </ul>
              </div>
          </div>
      </div>
        <div>
          <div class="box" id="recruit-box" style="height: fit-content;">
              <p class="box-title">작성한 모집글</p>
              <div class="accordion accordion-flush" id="accordionFlushExample">
                <div class="accordion-item" v-for="recruit in state.userRecruits" :value="recruit" :key="recruit.id" :recruit="recruit">
                  <h2 class="accordion-header">
                    <button @click="fetchApplicationList(recruit.id)" class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="`#flush-collaps${recruit.id}`" aria-expanded="false" :aria-controls="`#flush-collapse${recruit.id}`">
                      {{ recruit.title }}&nbsp;&nbsp;&nbsp;
                      <!-- <span v-if="state.recruitApplications.length" class="badge text-bg-primary rounded-pill">{{state.recruitApplications.length}}</span> -->
                    </button>
                  </h2>
                  <div :id="`flush-collaps${recruit.id}`" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body" v-for="apply in state.recruitApplications" >
                      <div class="apply-name">{{ apply.name }}</div>
                      <div class="apply-buttons">
                        <span class="badge text-bg-success" @click="acceptApplication(apply.id, apply.recruitUserId, apply.recruitPostId)">수락</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span class="badge text-bg-danger" @click="rejectApplication(apply.id)">거절</span>
                      </div>
                    </div>
                    <hr>
                    <div class="moveNext" style="text-align: center; margin-bottom: 15px;" @click="navigateTo(`/recruit/${recruit.id}`)">모집글으로 이동</div>
                  </div>
              </div>
            </div>
          </div>
      </div>
    </div>
    <Footer></Footer>
  </div>

<!-- 3개
    모집글 펼치면 신청 목록
    수정하기 버튼 추가  -->
</template>

<script setup>
  import { useRoute, useRouter } from 'vue-router';
  import { reactive, ref, onMounted } from 'vue';
  import UserProps from '@/components/MyPage/UserProps.vue';
  import Header from "@/components/Header/Header.vue";
  import Footer from "@/components/Footer/Footer.vue";

  const router = useRouter();
  const route = useRoute();
  const id = route.params.id;

  const state = reactive({
    userInfo: {},
    userStudyclubs: {},
    userApplications: {},
    userLogs: {},
    userRecruits: {},
    recruitApplications: {},
    selectedRecruit: {}
  });

  var count = ref();

  const fetchUserInfo = async() => {
    try {
      const response = await fetch(`/api/user/${id}`);

      if(!response.ok) {
          throw new Error('response is not ok');
      }
      
      const data = await response.json();
      state.userInfo = data;

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchUserStudyclub = async() => {
    try {
      const response = await fetch(`/api/user/join-studyclub/${id}`);

      if(!response.ok) {
          throw new Error('response is not ok');
      }

      const data = await response.json();
      state.userStudyclubs = data;

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchUserApplication = async() => {
    try {
      const response = await fetch(`/api/recruit-apply/user/${id}`);

      if(!response.ok) {
          throw new Error('response is not ok');
      }

      const data = await response.json();
      state.userApplications = data;
    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchUserLog = async() => {
    try {
      const response = await fetch(`/api/studylog/find-writing-studyclublog/${id}`);

      if(!response.ok) {
          throw new Error('response is not ok');
      }

      const data = await response.json();
      state.userLogs = data;

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }
  
  const fetchUserRecruit = async() => {
    try {
      const response = await fetch(`/api/recruit/list/${id}`);

      if(!response.ok) {
          throw new Error('response is not ok');
      }

      const data = await response.json();
      state.userRecruits = data;

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchApplicationList = async(recruitId) => {
    try {
      const response = await fetch(`/api/recruit-apply/post/${recruitId}`);

      if(!response.ok) {
          throw new Error('response is not ok');
      }

      const data = await response.json();
      state.recruitApplications = data;



    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
  }

  const fetchRecruit = async(recruitId) => {

      try {
          const response = await fetch(`/api/recruit/detail/${recruitId}`);
          
          if(!response.ok) {
              throw new Error('response is not ok');
          }

          const data = await response.json();
          state.selectedRecruit = data;

          return state.selectedRecruit["clubId"];

      } catch(error) {
          console.error('fetch error: ' + error.message);
      }
    }

  const acceptApplication = async(applyId, userId, recruitId) => {
     
    const modifyData = {
      recruitStatus: "수락"
    }

    try {
      const response = await fetch(`/api/recruit-apply/accept/${applyId}`, {
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json'
          },
              body: JSON.stringify(modifyData)               
      });

      if(!response) {
          throw new Error('Network response was not ok');
      }

    } catch(error) {
        console.error('There was a problem with the fetch operation:', error.message);
    }

    const postData = {
      memberId: userId,
      studyclubId: await fetchRecruit(recruitId),
      memberType: 'T'
    }

    try {
      const response = await fetch('/api/studyclub-member/insert-member', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(postData)               
      });

      if(!response) {
          throw new Error('Network response was not ok');
      }

      alert("수락 처리되었습니다!");

    } catch(error) {
        console.error('There was a problem with the fetch operation:', error.message);
    }
  }

  const rejectApplication = async(applyId) => {
     
     const modifyData = {
       recruitStatus: "거절"
     }
 
     try {
       const response = await fetch(`/api/recruit-apply/reject/${applyId}`, {
           method: 'PUT',
           headers: {
               'Content-Type': 'application/json'
           },
               body: JSON.stringify(modifyData)               
       });
 
       if(!response) {
           throw new Error('Network response was not ok');
       }
 
     } catch(error) {
         console.error('There was a problem with the fetch operation:', error.message);
     }
   }

   const navigateTo = (path) => {
        router.push(path);
    }

  onMounted(async() => {
    await fetchUserInfo();
    await fetchUserStudyclub();
    await fetchUserApplication();
    await fetchUserLog();
    await fetchUserRecruit();

  });
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  height: max-content;
}
.profile {
  display: flex;
  justify-content: center;
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
.profile img {
  margin: 20px 0px;
  height: 200px;
  width: 200px;
}
.box {
  flex: 1;
  border: 1px solid black;
  padding: 20px;
  margin: 20px;
}
.box-title {
    text-align: center;
    font-size: 20px;
    font-family: '감탄로드돋움체 Bold';
    src: url('@/assets/fonts/감탄로드돋움체 Bold.ttf') format('truetype');
}
.box-content {
  margin-top: 40px;
  padding: 0px, 20px;
}
.nickname {
  font-size: 30px;
  margin: 20px;
}
.user-info {
  width: 100%;
  text-align: center;
  margin-bottom: 20px;
}
.accordion-body {
  display: flex;
  justify-content: space-between;
}
.apply-name {
  width: fit-content;
}
.apply-buttons {
  display: flex;
  justify-content: space-between;
  cursor: pointer;
}
#recruit-box {
  margin-bottom: 100px;
}
.list-group {
  cursor: pointer;
}
.accordion-button {
  cursor: pointer;
}
.moveNext {
  cursor: pointer;
}
</style>