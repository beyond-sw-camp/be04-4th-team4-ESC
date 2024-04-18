<template>
    
    <div class="wrapper">
        <!-- <p class="login">로그인 전 화면~</p> -->
        <!-- <div class="first"><img src="@/assets/boring.gif"></div> -->
        
        <div class="first">
            <div class="title-area">
                <div class="title">"혼자서의 영어 공부, 한계에 부딪히셨나요?"</div>
                <div class="english">영어 자격증 취득은 혼자서의 공부로는 어렵습니다.<br>
                    의욕 유지, 효과적인 학습 방법 찾기, 피드백 부족 등의 문제가 있죠. </div>
                </div>
                <div class="student">
                    <div class="button2" @click="loginCheck()">모집글 작성하러 가기</div>
                    <img src="@/assets/student.png">
                </div>
            </div>
        <div class="third">
            <div class="title-area2">
                <div class="title2">"함께하는 스터디로 영어 자격증 취득!<br> 나만의 스터디 파트너를 찾아보세요."</div>
                <div class="studyclub">ESC에서는 이런 고민을 함께 해결해 나갈 수 있는 플랫폼을 제공합니다.<br>
                    일정을 계획하고, 로그를 작성하며, 시험까지 함께 준비해나가는 과정에서 함께 공부하는<br>
                    스터디원들과의 활발한 토론은 당신의 학습을 더욱 효율적으로 만들어줄 것입니다.<br>
                </div>
                
            </div>
            <div class="together">
                <img src="@/assets/together.png" style="padding-top: 20px; margin-left: -30px">
                <div class="button3" @click="navigateTo('/recruit-list')">모집글 목록 보러가기</div> 
            </div>
        </div>
        <div class="second">
            <img src="@/assets/alphabet.png" style="margin-top: 30px">
            <div class="description">
                <div style="font-size: 25px;">"함께 성장하는 영어 자격증 스터디, 지금 시작하세요!"</div>
                <div class="button" @click="loginCheck2">스터디클럽 생성하러 가기</div>       
            </div>
        </div>
    </div>
</template>
<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from "vue-router";
import axios from "axios";
import router from '@/router/router';

const navigateTo = (path) => {
        router.push(path);
    }

function loginCheck() {

    if (token) {
        alert('스터디클럽 생성 이후 모집글 작성으로 이동합니다.')
        router.push(`/studyclub-regist/${userInfo.value.id}`);

    } else {
        alert('로그인이 필요합니다.')
        return false;
    }
}

function loginCheck2() {

if (token) {
    router.push(`/studyclub-regist/${userInfo.value.id}`);

} else {
    alert('로그인이 필요합니다.')
    return false;
}
}

const userInfo = ref(null);
const loaded = ref(false);
const token = localStorage.getItem('token'); 

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

onMounted(() => {
    if (token) {
    fetchUserInfo(token);

    } else {
    console.error('토큰이 없습니다.');
    }
});    


</script>

<style scoped>
    .login {
        height: 800%;
    }
    .first {
        width: 100%;
        height: 100vh;
        display: flex;
        flex-direction: column;
        margin-bottom: 20px;
    }
    .title-area2 {
        text-align: right;
    }
    .title {
        margin-top: 120px;
        margin-left: 50px;
        margin-bottom: 40px;
        font-size: 30px;
    }
    .title2 {
        margin-top: 80px;
        margin-right: 30px;
        margin-bottom: 40px;
        font-size: 30px;
    }
    .english {
        font-size: 20px;
        margin-left: 50px;
        margin-bottom: 30px;
        text-align: left;
    }
    .student {
        width: 100%;
        display: flex;
        justify-content: flex-end;
        margin-bottom: 60px;
    }
    .first img {
        margin-top: auto;
    }
    .second {
        width: 100%;
        height: 100vh;
        display: flex; 
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }   
    .second img {
        width: 40%;
        height: fit-content;
        margin: 40px;
    }
    .description {
        display: flex;
        flex-direction: column;
        align-items: center;  
        width: 100%;
        margin: 80px;
    }
    .description div {
        font-size: 20px;
        font-style: bold;
        margin-bottom: 20px;
    }
    .button {
        font-size: 30px;  
        width: 350px;
        margin: 30px;
        padding: 10px;
        text-align: center;
        border: 1px solid black;
        border-radius: 15px;
    }
    .button2 {
        font-size: 20px;
        width: 350px;
        height: 50px;
        margin: 30px;
        padding: 10px;
        text-align: center;
        border: 1px solid black;
        border-radius: 15px;
    }
    .button3 {
        font-size: 20px;
        width: 350px;
        height: 50px;
        margin: 0px 30px;
        padding: 10px;
        text-align: center;
        border: 1px solid black;
        border-radius: 15px;
    }
    .third {
        width: 100%;
        height: 100vh;
        display: flex;
        flex-direction: column;
        margin-bottom: 70px;
    }
    .together {
        display: flex;
        justify-content: flex-start;
        margin-top: auto;
        margin-bottom: 80px;
    }
    .studyclub {
        font-size: 20px;
        margin-bottom: 60px;
    }
</style>