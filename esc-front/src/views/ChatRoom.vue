<template>
    <div class="container">
        <div class="chatbox-header">
            <div class="colorPanel"
                :style="{ display: colorPanelShown ? 'flex' : 'none', opacity: colorPanelShown ? 'flex' : 'none' }">
                <div v-for="color in colors" class="colorPanel__colorSlot">
                    <div class="colorPanel__color" :style="{ background: color }" @click="setColor(color)"></div>
                </div>
            </div>
            <div class="chatbox__header">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                    class="bi bi-box-arrow-left room-out" viewBox="0 0 16 16" @click="moveBack">
                    <path fill-rule="evenodd"
                        d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0z" />
                    <path fill-rule="evenodd"
                        d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708z" />
                </svg>
                <div class="chatbox__headerText">
                    {{ roomName }}
                    <div> ({{ sessionNo }})</div>
                    <div class="chatbox__onlineDot"></div>
                </div>
                <div class="chatbox__button" :style="{ background: chatColor }"
                    @click="colorPanelShown = !colorPanelShown"></div>
            </div>
        </div>
        <div id="app" class="chatbox">
            <div class="chatbox__messages">
                <div v-for="message in messages" :key="message.id" class="chatbox__messageBox"
                    :class="{ 'chatbox__messageBox--primary': message.primary }">
                    <div class="chatbox-sender" :class="{ 'chatbox__sender--primary': message.primary }"> {{
                        message.sender }} </div>
                    <div class="chat-message" :class="{ 'chat-message--primary': message.primary }">
                        <div class="chatbox__message" :class="{ 'chatbox__message--primary': message.primary }"
                            :style="{ background: chatColor }">
                            {{ message.text }}
                        </div>
                        <div class="chatbox__date" :class="{ 'chatbox__date--primary': message.primary }">
                            {{ message.date }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="chatbox-input">
            <div class="chatbox__inputPanel">
                <input v-model="newMessage" @keyup.enter="send" class="chatbox__input" placeholder="메세지를 입력해주세요 !" />
                <div class="chatbox__tooltip chatbox__tooltip--bottom"
                    :style="{ opacity: newMessage.length > 2 && !tutorialSeen ? 0.7 : 0 }">Enter!
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import moment from 'moment';
import axios from 'axios';
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';


const socket = ref(null);
const roomId = useRoute();
const roomName = ref('');
const router = useRouter();

function moveBack() {
    router.push('/chat/list');
}

// 토큰 관련


const userInfo = ref(null);
const loaded = ref(false);

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

//

onMounted(async () => {

    const response = await axios.get(`http://localhost:30003/chat/room/id/${roomId.params.id}`);
    const name = ref(response.data.roomName);
    roomName.value = name.value;

    const socketUrl = `ws://localhost:30003/chat/${roomId.params.id}`;

    socket.value = new WebSocket(socketUrl);
    socket.value.onopen = function (event) {
        console.log('WS connection is stable! ~uWu~');
    };

    socket.value.onmessage = function (message) {
        const parsedMessage = JSON.parse(message.data);
        parsedMessage.forEach(msg => {
            const newChatMessage = {
                id: messages.value.length + 1,
                sender: msg.sender, // 발신자 정보 추가
                text: msg.text,
                primary: false, // 발신자 = 수신자일 경우 true, 아니면 false
                date: moment().format('YY.MM.DD hh:mm'),
                sessionNo: msg.sessionNo
            };
            sessionNo.value = msg.sessionNo;
            messages.value.push(newChatMessage);
        });
        console.log('message:', messages.value);        // parsedMessage가 전부 쌓이는 구조 
        console.log('Got a message from the WS: ', parsedMessage);

    }

    watch(messages.value, scrollToBottom);

    const token = localStorage.getItem('token');

    if (token) {
        fetchUserInfo(token);

    } else {
        console.error('토큰이 없습니다.');
    }
})

function scrollToBottom() {
    const wrapper = document.querySelector(".chatbox");
    wrapper.scrollTop = wrapper.scrollHeight;
}


const chatColor = ref('#0084ff'); // 메시지 배경색 설정
const colorPanelShown = ref(false);
const newMessage = ref('');
const tutorialSeen = ref(false);
const colors = ref([
    '#0084ff', '#ffc300', '#4af844',
    '#7646ff', '#a695c7', '#ff5ca1',
    '#fa3c4c', '#f56b78', '#33343f'
]);
const messages = ref([]);
const sessionNo = ref(0);

function send() {
    if (newMessage.value.length > 0) {
        const newChatMessage = {
            id: messages.value.length + 1,
            sender: userInfo.value.nickname,   // 토큰에서 닉네임 가져와서 넣기 
            text: newMessage.value,
            primary: true, // 내가 보낸 메시지는 오른쪽에 표시됨
            date: moment().format('YY.MM.DD hh:mm'),
            sessionNo: 0
        };
        messages.value.push(newChatMessage);
        socket.value.send(JSON.stringify([newChatMessage]));
        newMessage.value = '';
        tutorialSeen.value = true;
    }
}

function setColor(color) {
    chatColor.value = color;
    colorPanelShown.value = false;
}


</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Roboto');

body {
    margin: 0;
    font-family: 'Roboto';
}

.room-out {
    margin: 0 0 0 10px;
}

.room-out:hover {
    cursor: pointer;
}

.chat-message {
    display: flex;
    align-items: flex-end;
}

.chat-message--primary {
    align-items: flex-end;
    flex-direction: row-reverse;
}

.container {
    display: block;
    position: relative;
    max-width: 700px;
    min-width: 400px;
    margin: 30px auto;
    padding: 10px;
    background: white;
    border-radius: 15px;
    box-shadow: 0 0 30px #eee;
    font-size: 17px;
}

.chatbox-header {
    display: block;
    position: relative;
    max-width: 700px;
    padding: 10px;
    background: white;
    border-radius: 15px;
    box-shadow: 0 0 30px #eee;
    font-size: 17px;
}

.chatbox-input {
    display: block;
    position: relative;
    max-width: 700px;
    padding: 10px;
    background: white;
    border-radius: 15px;
    box-shadow: 0 0 30px #eee;
    font-size: 17px;
}

.visible {
    opacity: 1;
}

.colorPanel {
    position: absolute;
    margin: 70px auto;
    left: 0;
    right: 0;
    height: 200px;
    width: 200px;
    z-index: 3;
    background: white;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    border-radius: 15px;
    box-shadow: 0 0 30px #aaa;
    transition: all 0.12s;
}

.colorPanel__colorSlot {
    box-sizing: border-box;
    width: 33%;
    height: 33%;
    padding: 10px;
}

.colorPanel__color {
    height: 100%;
    width: 100%;
    border-radius: 100px;
    cursor: pointer;
    background: #eee;
    transition-duration: 0.12s;
}

.colorPanel__color:hover {
    opacity: 0.7;
}

.chatbox {
    display: block;
    position: relative;
    max-width: 700px;
    height: 500px;
    overflow-y: scroll;
    -ms-overflow-style: none;
    scroll-behavior: smooth;
    padding: 10px;
    background: white;
    border-radius: 15px;
    box-shadow: 0 0 30px #eee;
    font-size: 16px;
}

.chatbox::-webkit-scrollbar {
    display: none;
}

@keyframes blinking {
    0% {
        opacity: 1;
    }

    50% {
        opacity: 0.3;
    }

    100% {
        opacity: 1;
    }
}

.chatbox__header {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: nowrap;
    font-weight: 900;
    padding: 10px 0;
    width: 100%;
    text-align: center;
    cursor: default;
    justify-content: space-between;
    border-bottom: 2px solid #f5f5f5;
}

.chatbox__headerText {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    padding-left: 20px;
}

.chatbox__onlineDot {
    display: block;
    background: #00C853;
    height: 10px;
    width: 10px;
    border-radius: 10px;
    margin-left: 5px;
    animation-name: blinking;
    animation-duration: 1.5s;
    animation-iteration-count: infinite;
}

.chatbox__tooltip {
    opacity: 0;
    z-index: 2;
    width: auto;
    font-size: 12px;
    text-align: center;
    padding: 3px 10px;
    cursor: default;
    position: absolute;
    color: rgb(173, 166, 166);
    box-sizing: border-box;
    border-radius: 15px;
    background: #111;
    transition: all 0.14s;
}

.chatbox__tooltip--bottom {
    top: 115%;
    margin-left: 110px;
    opacity: 0.2;
}

.chatbox__tooltip--left {
    right: calc(100% + 5px);
    right: -moz-calc(100% + 5px);
    margin-left: 5px;
    top: 5px;
}

.chatbox__messages {
    width: 100%;
    min-height: 400px;
    padding: 10px;
    box-sizing: border-box;
}

.chatbox__messageBox {
    /* width: 100%; */
    margin-top: 5px;
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.chatbox__sender--primary {
    align-items: flex-end;
}

.chatbox__messageBox--primary {
    flex-direction: column;
    align-items: flex-end;
}

.chatbox__date {
    position: relative;
    background-color: white;
    color: rgb(127, 125, 125);
    font-size: 12px;
    margin: 0 10px 0 10px;
    opacity: 0.5;
}

.chatbox__date--primary {
    background: white;
}

.chatbox__message {
    position: relative;
    display: inline-block;
    padding: 5px 20px;
    border-radius: 15px;
    cursor: default;
    background: #f1f1f1;
    color: white;
    transition: all 0.12s;
}

.chatbox__message:hover {
    opacity: 0.7;
}

.chatbox__message--primary {
    color: white;

}

.chatbox__inputPanel {
    width: 100%;
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.chatbox__input {
    font-family: 'Roboto';
    font-size: 1em;
    width: 100%;
    border: none;
    background: #f3f3f3;
    margin: 0 10px;
    padding: 5px 15px;
    color: #444;
    border-radius: 15px;
    box-sizing: border-box;
}

.chatbox__button {
    display: inline-block;
    height: 20px;
    width: 22px;
    color: white;
    border-radius: 15px;
    background: #0084ff;
    cursor: pointer;
    font-weight: 900;
    transition-duration: 0.1s;
}

.chatbox__button:hover {
    opacity: 0.7;
}

@media screen and (max-width: 450px) {
    .chatbox {
        box-sizing: border-box;
        margin: 0;
        max-width: 100%;
        width: 100%;
        border-radius: 0;
        height: 100%;
    }
}
</style>
