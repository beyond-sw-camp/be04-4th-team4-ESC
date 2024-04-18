<template>
    <div>
        <div class="login">
            <div class="title">
                <h1>English Study Club</h1>
            </div>
            <div class="nameTitle">
                <h5>이름</h5>
                <input type="text" class="nameBox" placeholder="이름을 입력하세요" v-model.trim="memberName">
            </div>
            <div class="nicknameTitle">
                <h5>닉네임</h5>
                <input type="text" class="nicknameBox" placeholder="닉네임을 입력하세요" v-model.trim="memberNickname">
            </div>
            <div class="findIddiv">
                <button type="button" class="findIdBtn" @click="findId()">아이디 찾기</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from "axios";
import router from '@/router/router';

const memberName = ref('');
const memberNickname = ref('');
const email = ref('');


const findId = async () => {
    await axios.get(`/api/user/findId?name=${memberName.value}&nickname=${memberNickname.value}`)
    .then(response => {
        console.log(response)
        if(response.status == 200) {
            email.value = response.data;
            router.push(`/findIdResult/${email.value}`);
        }
        else {
            alert('이름과 이메일을 다시 확인해주세요.')
            return false;
        }
    })
};

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

.nameTitle{
    width: 100%;
    margin-top: 10%;
    font-size: 12px;

}

.nameBox {
    width: 100%;
    height: 40px;
}

.nicknameTitle {
    width: 100%;
    font-size: 12px;
}

.nicknameBox {
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

.findIddiv {
    width:100%;
    margin-top:20%;
    margin-bottom: 20%;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;

}

.findIdBtn {
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
}
</style>