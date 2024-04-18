<template>
    <div>
        <div class="reset">
            <div class="title">
                <h1>English Study Club</h1>
            </div>
            <div class="nameTitle">
                <h5>이름</h5>
                <input type="text" class="nameBox" placeholder="이름을 입력하세요" v-model.trim="memberName">
            </div>
            <div class="emailTitle">
                <h5>이메일</h5>
                <div class="confirmdiv">
                    <input type="text" id="emailBox" class="emailBox" placeholder="이메일을 입력하세요" v-model.trim="memberEmail">
                    <button class="confirm" :disabled="!memberEmail.trim()" @click="emailExCheck()">확인</button>
                </div> 
            </div>
            <div class="certificationTitle">
                <h5>인증번호</h5>
                <div class="confirmdiv">
                    <input type="text" id="certificationBox" class="certificationBox" placeholder="인증번호를 입력하세요" v-model.trim="certification">
                    <button class="trans" id="transBox" @click="emailSend()" disabled>전송</button>
                    <button class="transconfirm" id="confirmBox" @click="certificationNumCheck()" disabled>확인</button>
                </div>
            </div>
            <div class="resetdiv">
                <button type="button" class="resetBtn" @click="inputCheck()">비밀번호 재설정</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from "axios";
import router from '@/router/router';

const memberEmail = ref('');
const memberName = ref('');
const certification = ref('');
const memberNickname = ref('');
let emailcheck = false;
let certificationNum;
let NumCheck = false;
let userCheck = false;



function inputCheck() {
    if(memberName.value == ''){
        alert('이름을 입력해주세요.');
        return false;
    } else if(memberEmail.value == ''){
        alert('이메일을 입력해주세요.');
        return false;
    } else if(emailcheck != true ) {
        alert('이메일 확인버튼을 눌러주세요.');
        return false;
    } else if(NumCheck != true) {
        alert('인증번호 확인버튼을 눌러주세요.');
        return false;
    } else {
        return UserCheck();
    }
}

const emailExCheck = async () => {
    await axios.get(`/api/user/emailExCheck/${memberEmail.value}`)
    .then(response => {
        if(response.data == true) {
            alert('이메일이 확인되었습니다.');
            emailcheck = true;
            let disable = document.querySelector('#emailBox');
            disable.disabled = true;
            let transDisable = document.querySelector('#transBox');
            transDisable.disabled = false;
        }
        else {
            alert('없는 이메일입니다.')
            return false;
        }
    })
};


const emailSend = async () => {
    await axios.post("/api/mailSend", {email : memberEmail.value})
    .then(response => {
        certificationNum = response.data;
        let disableConfirmBox = document.querySelector('#confirmBox');
        disableConfirmBox.disabled = false;
    })
}

function certificationNumCheck() {
    if(certification.value == certificationNum) {
        alert('인증번호가 일치합니다.');
        NumCheck = true;
        let disable = document.querySelector('#certificationBox');
        disable.disabled = true;
    }
    else {
        alert('인증번호가 일치하지 않습니다.');
        return false;
    } 
}

const UserCheck = async () => {
    await axios.get(`/api/user/checkUser?name=${memberName.value}&email=${memberEmail.value}`)
    .then(response => {
        if(response.data == true) {
            alert('회원정보가 확인되었습니다.')
            router.push(`/resetPasswordFinal/${memberEmail.value}`);
        }
        else {
            alert('이름과 이메일에 일치하는 회원정보가 없습니다.')
            return false;
        }
    })
};
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

.nameTitle {
    width: 98%;
    font-size: 12px;
}

.nameBox {
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

.emailTitle {
    width: 98%;
    font-size: 12px;
}

.certificationTitle {
    width: 98%;
    font-size: 12px;
}

.emailBox {
    width: 98%;
    height: 30px;
}

.certificationBox {
    width: 98%;
    height: 30px;
}

.confirmdiv {
    width: 100%;
    display: grid;
    grid-template-columns: 4.5fr 0.5fr 1fr 0.2fr 1fr;
    justify-items: center;
}

.trans {
    width: 100%;
    grid-column-start: 3;
    grid-column-end: 4;
}

.transconfirm {
    width: 100%;
    grid-column-start: 5;
    grid-column-end: 6;
}


.confirm {
    width: 100%;
    grid-column-start: 3;
    grid-column-end: 6;
}
</style>