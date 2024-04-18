<template>
    <section>
        <div class="titleAndSearch">
            <div class="board-title" @click="resetList">
                <h2>채팅방 리스트</h2>
            </div>
            <div class="search" id="search-1">
                <div class="searchText">
                    <input type="text" id='searchText' class="form-control" placeholder="Search..."
                        v-model="search_condition" @keyup.enter="callData">
                </div>
                <div class="">
                    <button class="btn" id="searchBtn" type="button" @click="callData">검색</button>
                </div>

                <!-- Button to Open the Modal -->
                <button type="button" class="btn btn-primary" id="newBtn" data-bs-toggle="modal"
                    data-bs-target="#myModal">
                    채팅방 생성
                </button>

                <div class="modal" id="myModal">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <h4 class="modal-title">새 채팅방 생성</h4>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>

                            <div class="modal-body">
                                <div>
                                    <input type="text" class="newRoomInput" v-model="newRoomName">
                                    <button type="button" @click="createNewRoom" class="newRoomBtn"
                                        data-bs-dismiss="modal"> 생성하기 </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <article class="main-article">
            <hr>
            <div class="chatWrapper">
                <div v-if="isTrue">
                    <ChatRoomContainer />
                </div>
            </div>
        </article>

    </section>
</template>

<script setup>
import Header from "@/components/Header/Login_Header.vue";
import Footer from "@/components/Footer/Footer.vue";
import { ref, provide } from 'vue';
import ChatRoomContainer from '../components/ChatRoom/ChatRoomContainer.vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const search_condition = ref('');
const roomList = ref([]);
const isTrue = ref(true);

/* 입력한 정보 받아서 axios로 호출 */
async function callData() {

    const getUrl = `/api/chat/room/title/${search_condition.value}`;

    const response = await axios.get(`/api/chat/room/title/${search_condition.value}`);

    roomList.value = response.data;

    if (roomList.value.length == 0) {
        isTrue.value = false;
    } else {
        isTrue.value = true;
    }
}

provide('roomList', roomList);

const newRoomName = ref('');
const router = useRouter();
function resetList() {
    router.go(0);
}

async function createNewRoom() {

    const final = await axios.get(`http://localhost:30003/chat/room/last`);
    const newRoomId = final.data + 1;


    await axios.post(`http://localhost:30003/chat/room`, {
        id: newRoomId,
        roomName: newRoomName.value,
        roomHostId: 1
    })

    await router.go(0);
}

</script>

<style scoped>
section {
    display: grid;
    min-width: 455px;
    min-height: 800px;
    /* grid-template-columns: 0.8fr 3fr 0.8fr; */
    grid-template-columns: 100%;
    grid-template-rows: 101.61px 5px auto;
    grid-template-areas:
        /* "left  title   right"
        "left   none    right"
        "left   main    right"; */
        "   title   "
        "   none    "
        "   main    ";
    padding: 0;
    margin: 0;
    margin-left:10%;
    margin-right:10%;
    width:80%;

}

.chatWrapper {
    display: block;
    position: relative;
    padding: 10px;
    background: white;
    border-radius: 15px;
    box-shadow: 0 0 30px #eee;
    height: 500px;
    overflow-y: scroll;
}


hr {
    margin: 5px;
}

.main-article {
    grid-area: main;
    display: flex;
    flex-wrap: wrap;
    flex-direction: column;

}

.left-sidebar {
    grid-area: left;
}

.right-sidebar {
    grid-area: right;
}


h2 {
    color: black;
    margin: 0;
    padding: 0;
    font-weight: bold;
}

.titleAndSearch {
    display: flex;
    grid-area: title;
}

.board-title {
    display: flex;
    align-items: flex-end;
    width: 40%;
    height: 100%;
    margin: 0;
    padding: 20 0 0 0;
    font-size: 30px;
}

.board-title :hover {
    cursor: pointer;
}

.search {
    display: flex;
    flex-wrap: wrap;
    width: 80%;
    height: 100%;
    flex-direction: row;
    align-items: flex-end;
    justify-content: flex-end;
    margin: 0;
    padding: 0;
}

.searchBar {
    margin: 0, auto;
    border-radius: 4px;
}

.searchText {
    height: 30px;
}

#newBtn {
    background-color: #000000;
    color: white;
    padding: 6px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    height: 30px;
}

#searchText {
    height: 30px;
    border-radius: 4px;
    font-size: 12px;
}

#searchBtn {
    background-color: #000000;
    color: white;
    padding: 4px 10px;
    margin: 0px 3px 0px 3px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    height: 30px;
}

.newRoomInput {
    height: 30px;
    border-radius: 4px;
    font-size: 12px;
    width: 80%;
}

.newRoomBtn {
    background-color: #000000;
    color: white;
    padding: 4px 10px;
    margin: 0px 3px 0px 3px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    height: 30px;
}

.no-search-result {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 50px;
}
</style>