<template>
    <div>
        <div class="reset">
            <div class="title">
                <h1>English Study Club</h1>
            </div>
            <div class="pwdTitle">
                <h2>비밀번호</h2>
                <input type="password" class="pwdBox" placeholder="비밀번호를 입력하세요" v-model.trim="memberPassword">
                <p>*특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내로 작성</p>
            </div>
            <div class="pwdConfirm">
                <h2>비밀번호 확인</h2>
                <input type="password" class="pwdConfirmBox" placeholder="비밀번호를 확인하세요" v-model.trim="passwordConfirm">
            </div>
            <div class="resetdiv">
                <button type="button" class="resetBtn" @click="inputCheck()">비밀번호 재설정</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from "vue-router";
import axios from "axios";
import router from '@/router/router';

const memberPassword = ref('');
const passwordConfirm = ref('');
const memberEmail = useRoute();
let pwdConditionCheck = false;



function inputCheck() {
    if (memberPassword.value == ''){
        alert('비밀번호를 입력해주세요.');
        return false;
    } else if (pwdCondition(memberPassword.value) == false) {
        alert('올바른 비밀번호 형식이 아닙니다.');
        return false;
    }
    else if (memberPassword.value != passwordConfirm.value) {
        alert('비밀번호가 일치하지 않습니다.');
        return false;
    } else {
        return reset();
    }
}

const reset = async () => {
    await axios.post("/api/user/resetPassword",{   
        email: memberEmail.params.email,
        password: memberPassword.value
    }).then ((response) => {
        if(response.status == 200 && response.data == true) {
            alert('비밀번호가 재설정 되었습니다!')
            router.push('/login'); 
        }
        else if(response.status == 200 && response.data == false) {
            alert('이전에 설정한 비밀번호와 같습니다.')
            return false;
        }
    }).catch ((e) => {
        alert('입력한 정보가 유효하지 않습니다. 아이디와 비밀번호를 확인해주세요.');
    
    })
};

function pwdCondition(password) {
    var pattern = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    if(pattern.test(password) == true){
        return true;
    }
    else {
        pwdConditionCheck = false;
        return false;
    }
}
</script>

<style scoped>
.reset {
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

.pwdTitle {
    width: 98%;
    font-size: 12px;
}

.pwdTitle p {
    margin-top: 1px;
}

.pwdBox {
    width: 98%;
    height: 30px;
}

.resetdiv {
    width:100%;
    margin-top:20%;
    margin-bottom: 20%;
    display: grid;
    grid-template-columns: 1fr 3fr 1fr;
    justify-items: center;

}

.resetBtn {
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

.pwdConfirm {
    width: 98%;
    font-size: 12px;
}

.pwdConfirmBox {
    width: 98%;
    height: 30px;
}
</style>