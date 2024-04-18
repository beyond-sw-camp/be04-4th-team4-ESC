<template>
    <div>
        <div class="login">
            <div class="title">
                <h1>English Study Club</h1>
            </div>
            <div class="content">
                <span>회원님의 아이디는</span><br>
                <span>{{ memberEmail.params.email }}</span>
                <span>입니다.</span>
            </div>
            <div class="logindiv">
                <button type="button" class="loginBtn" @click="login()">로그인</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import router from "@/router/router";
import { useRoute } from "vue-router";

const memberEmail = useRoute();

function login() {
    router.push('/login');
}
</script>

<style scoped>
.login {
    width:40%;
    margin-left: 30%;
    margin-right: 30%;
    display: grid;
    grid-template-rows: 1fr 1fr 1fr 1fr 1fr;
    align-items: center;

}

.title {
    text-align: center;
    margin-top: 5%;
    font-size: 20px;
    
}

.content{
    width: 100%;
    font-size: 20px;
    text-align: center;
    align-items: center;
    grid-row-start: 3;
    grid-row-end: 4;
}

.logindiv {
    width:100%;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;
    grid-row-start: 5;
    grid-row-end: 6;

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


</style>