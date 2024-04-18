<template>
    <div>
        <div class="login">
            <div class="title">
                <h2>English Study Club</h2>
            </div>
            <div class="idTitle">
                <h5>아이디</h5>
                <input type="text" class="idBox" placeholder="아이디를 입력하세요" v-model.trim="email">
            </div>
            <div class="pwdTitle">
                <h5>비밀번호</h5>
                <input type="password" class="pwdBox" placeholder="비밀번호를 입력하세요" v-model.trim="password">
            </div>
            <div class="bottomtext">
                <span class="left" @click="signup()">회원가입</span>
                <span class="right" @click="resetPwd()">비밀번호 찾기</span>
                <span class="division">|</span>
                <span class="right" @click="findId()">아이디 찾기</span>           
            </div>
            <div class="logindiv">
                <button type="button" class="loginBtn" @click="inputCheck()">로그인</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { provide, ref } from 'vue';
import axios from "axios";
import { RouterLink } from 'vue-router';
import router from '@/router/router';


const email = ref('');
const password = ref('');

function signup() {
    router.push('/regist');
}

function findId() {
    router.push('/findId');
}

const tokenData = async () => {
    await axios.post("/api/login",
    {
        email: email.value,
        password: password.value
    }).then ((response) => {    // then: post 요청 성공 시 동작할 콜백 함수 등록
        if(response.status == 200) {
            // 토큰 및 아이디 로컬 스토리지에 저장
            localStorage.setItem('token', response.headers.token);
            // localStorage.setItem('email', email.value)
            
            alert('로그인 되었습니다!')
            router.push('/'); 
        }
    }).catch ((e) => {
        alert('입력한 정보가 유효하지 않습니다. 아이디와 비밀번호를 확인해주세요.');
    })
};

function inputCheck() {
    if(email.value == ''){
        alert('아이디를 입력해주세요.');
        return false;
    } else if (password.value == ''){
        alert('비밀번호를 입력해주세요.');
        return false;
    } else {
        // 서버 연동하여 토큰값 가져온 후 유효성 검사 코드 추가
        return tokenData();
    }
}

function resetPwd() {
    router.push('/resetPassword');
}
</script>

<style scoped>
.login {
    width:40%;
    margin-left: 30%;
    margin-right: 30%;
    display: grid;
}

.title {
    text-align: center;
    margin-top: 5%;
    font-size: 20px;
    
}

.idTitle{
    width: 100%;
    margin-top: 10%;
    font-size: 12px;

}

.idBox {
    width: 100%;
    height: 40px;
}

.pwdTitle {
    width: 100%;
    font-size: 12px;
}

.pwdBox {
    width: 100%;
    height: 40px;
}

.right {
    float: right;
    margin-left: 2%;
    cursor: pointer;
}

.division {
    float: right;
    margin-left: 2%;
}

.left {
    cursor: pointer;
}

.logindiv {
    width:100%;
    margin-top:20%;
    margin-bottom: 20%;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;

}

.loginBtn {
    background-color: #515050;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    justify-content: center;
    display: flex;
    align-items: center;
    width: 100%;
    grid-column-start: 2;
    grid-column-end: 3;
}

.bottomtext {
    margin-top:1%;
    font-size:15px;
}
</style>