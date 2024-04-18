<template>
    <div>
        <div class="regist">
            <div class="title">
                <h3>English Study Club</h3>
            </div>
            <div class="nameTitle">
                <h5>이름</h5>
                <input type="text" class="nameBox" placeholder="이름을 입력하세요" v-model.trim="memberName">
            </div>
            <div class="emailTitle">
                <h5>이메일</h5>
                <div class="confirmdiv">
                    <input type="text" id="emailBox" class="emailBox" placeholder="이메일을 입력하세요" v-model.trim="memberEmail">
                    <button class="confirm" :disabled="!memberEmail.trim()" @click="emailCheck()">확인</button>
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
            <div class="pwdTitle">
                <h5>비밀번호</h5>
                <input type="password" class="pwdBox" placeholder="비밀번호를 입력하세요" v-model.trim="memberPassword">
                <p>*특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내로 작성</p>
            </div>
            <div class="pwdConfirm">
                <h5>비밀번호 확인</h5>
                <input type="password" class="pwdConfirmBox" placeholder="비밀번호를 확인하세요" v-model.trim="passwordConfirm">
            </div>
            <div class="nicknameTitle">
                <h5>닉네임</h5>
                <div class="confirmdiv">
                    <input type="text" class="nicknameBox" placeholder="닉네임을 입력하세요" v-model.trim="memberNickname">
                    <button class="confirm" id="nicknameBox" :disabled="!memberNickname.trim()" @click="nicknameCheck()">확인</button>
                </div>
            </div>
            <div class="registdiv">
                <button type="button" class="registBtn" @click="inputCheck()">가입하기</button>
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
const memberPassword = ref('');
const certification = ref('');
const passwordConfirm = ref('');
const memberNickname = ref('');
let emailcheck = false;
let certificationNum;
let NumCheck = false;
let emailConditionCheck = false;
let pwdConditionCheck = false;
let nameConditionCheck = false;
let nicknamecheck = false;
let email;

const regist = async () => {
    await axios.post("http://localhost:30003/user/regist",{   
        memberEmail: memberEmail.value,
        memberPassword: memberPassword.value,
        memberName: memberName.value,
        memberNickname: memberNickname.value
    }).then ((response) => {
        if(response.status == 201) {
            alert('회원가입이 완료되었습니다!');
            router.push('/login'); 
        }
    }).catch ((e) => {
        alert('입력한 정보가 유효하지 않습니다. 아이디와 비밀번호를 확인해주세요.');
    
    })
};

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
    } else if (memberPassword.value == ''){
        alert('비밀번호를 입력해주세요.');
        return false;
    } else if (pwdCondition(memberPassword.value) == false) {
        alert('올바른 비밀번호 형식이 아닙니다.');
        return false;
    }
    else if (memberPassword.value != passwordConfirm.value) {
        alert('비밀번호가 일치하지 않습니다.');
        return false;
    } else if (memberNickname.value == '') {
        alert('닉네임을 입력해주세요.');
        return false;
    } else {
        return regist();
    }
}

const emailCheck = async () => {
    await axios.get(`/api/user/emailCheck/${memberEmail.value}`)
    .then(response => {
        if(response.data == true) {
            emailCondition(memberEmail.value)
            if(emailConditionCheck == true) {
                alert('사용가능한 이메일입니다.')
                emailcheck = true;
                let disable = document.querySelector('#emailBox');
                disable.disabled = true;
                let transDisable = document.querySelector('#transBox');
                transDisable.disabled = false;
            } else {
                alert('잘못된 이메일 형식입니다.')
                return false;
            }
            
        }
        else {
            alert('이미 존재하는 이메일입니다.')
            return false;
        }
    })
};

const nicknameCheck = async () => {
    await axios.get(`/api/user/nicknameCheck/${memberNickname.value}`)
    .then(response => {
        if(response.data == true) {
            alert('사용가능한 닉네임입니다.')
            nicknamecheck = true;
            let disable = document.querySelector('#nicknameBox');
            disable.disabled = true;
        }
        else {
            alert('이미 존재하는 닉네임입니다.')
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

function emailCondition(email) {
    var pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if(pattern.test(email) == true){
        emailConditionCheck = true;
    }
    else {
        emailConditionCheck = false;
    }
}

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
.regist {
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
    width: 100%;
    height: 30px;
}

.nameTitle {
    width: 98%;
    font-size: 12px;
}

.nameBox {
    width: 100%;
    height: 30px; 
}

.registdiv {
    width:100%;
    margin-top:20%;
    margin-bottom: 20%;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;

}

.registBtn {
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

.emailTitle {
    width: 98%;
    font-size: 12px;
}

.certificationTitle {
    width: 98%;
    font-size: 12px;
}

.nicknameTitle {
    width: 98%;
    font-size: 12px;
}

.pwdConfirmBox {
    width: 100%;
    height: 30px;
}

.emailBox {
    width: 100%;
    height: 30px;
}

.certificationBox {
    width: 100%;
    height: 30px;
}

.nicknameBox {
    width: 100%;
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