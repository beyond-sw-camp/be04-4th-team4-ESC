<!-- 스터디 일정 하나 조회, 변경 -->
<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from 'axios';
import moment from 'moment';
import Header from "@/components/Header/Header.vue";
import Footer from "@/components/Footer/Footer.vue";

const route = useRoute();
const router = useRouter();

const id = route.params.id;

const title = ref();
const content = ref();
const start = ref();
const end = ref();
const useStatus = ref();
const studyclubId = ref();
const writerId = ref();
const participantList = ref();

const isReadOnly = ref(true);

const selectMember = ref('');

const userInfo = ref(null);
const loaded = ref(false);
const modifyCheck = ref(false);

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
                if (writerId.value == userInfo.value.id) {
                    modifyCheck.value = true;
                } else {
                    modifyCheck.value = false;
                }
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




const fetchStudySchedule = async (id) => {

    try {
        const response = await axios.get(`http://localhost:30003/study-schedule/schedule/${id}`);
        const data = response.data;
        const startDate = moment(data.start).format('YYYY-MM-DDTHH:mm');
        const endDate = moment(data.end).format('YYYY-MM-DDTHH:mm');

        title.value = data.title;
        content.value = data.content;
        start.value = startDate;
        end.value = endDate;
        useStatus.value = data.useStatus;
        studyclubId.value = data.studyclubId;
        writerId.value = data.writerId;
        participantList.value = data.studyScheduleParticipantList;
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error.message);
    }
};

const members = ref();
async function fetchMember() {
    try {
        const response = await axios.get(`http://localhost:30003/user/find-join-member-and-name/${studyclubId.value}`);
        const data = response.data;

        members.value = data;
    } catch (error) {
        console.error('이벤트 데이터를 불러오는 중 오류 발생:', error);
    }
}

// 선택된 멤버
const selectedMembers = ref([]);

// 선택하지 않은 멤버
const unselectedMembers = ref([]);

onMounted(async () => {
    await fetchStudySchedule(id);
    await fetchMember();


    for (const member of members.value) {
        if (participantList.value.includes(member.id)) {
            selectedMembers.value.push(member);
        } else {
            unselectedMembers.value.push(member);
        }
    }

    const token = localStorage.getItem('token');

    if (token) {
        fetchUserInfo(token);

    } else {
        console.error('토큰이 없습니다.');
    }
})

async function saveStudySchedule() {

    const startDate = moment(start.value, 'YYYY-MM-DDTHH:mm').format('YYYY-MM-DD HH:mm:ss');
    const endDate = moment(end.value, 'YYYY-MM-DDTHH:mm').format('YYYY-MM-DD HH:mm:ss');

    const select = [];
    if (selectedMembers.value.length > 0) {
        selectedMembers.value.forEach(member => {
            select.push(member.memberId);
        });
    }
    const sendData =
    {
        id: id,
        title: title.value,
        content: content.value,
        start: startDate,
        end: endDate,
        useStatus: useStatus.value,
        participantList: select,
    };

    await axios.put(
        `http://localhost:30003/study-schedule/modify`,
        sendData
    );

    this.isReadOnly = true;
}

async function removeStudySchedule() {

    await axios.put(
        `http://localhost:30003/study-schedule/remove/${id}`,
    );

    router.push(`/study-schedule/${studyclubId.value}`);
}

// 선택된 멤버 추가
function addParticipant(id) {
    const index = unselectedMembers.value.findIndex(member => member.id === id);
    if (index !== -1) {
        const selectedMember = unselectedMembers.value.splice(index, 1)[0];
        selectedMembers.value.push(selectedMember);
    }
}

// 선택된 멤버 삭제
async function removeParticipant(id) {
    const index = selectedMembers.value.findIndex(member => member.id === id);
    if (index !== -1) {
        const unselectedMember = selectedMembers.value.splice(index, 1)[0];
        unselectedMembers.value.push(unselectedMember);
    }
}

</script>

<template>
    <div class="all">
        <Header></Header>
        <div class="wrapper">
            <div class="container">
                <div class="title">스터디 일정</div>
                <div class="info">
                    <div class="form-floating" v-if="!isReadOnly">
                    <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 50px" v-model="title"></textarea>
                    <label for="floatingTextarea2">제목</label>
                    </div>
                    <div v-else class="content">{{ title }}</div>
                    <div class="form-floating" v-if="!isReadOnly">
                    <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 300px" v-model="content"></textarea>
                    <label for="floatingTextarea2">내용</label>
                    </div>
                    <div class="schedule-content" v-else>
                        <hr>
                        {{ content }}
                    </div>
                    <div class="date">시작 시간:
                        <input v-if="!isReadOnly" type="datetime-local" class="content" v-model="start">
                        <span v-else>{{ start }}</span>
                    </div>
                    <div class="date">종료 시간:
                        <input v-if="!isReadOnly" type="datetime-local" class="content" v-model="end">
                        <span v-else>{{ end }}</span>
                    </div>
                    <div class="member">
                        <div v-if="!isReadOnly">
                            참여 멤버:
                            <select class="content" v-model="selectMember" @change="addParticipant(selectMember.id)">
                                <option disabled value="">선택해주세요</option>
                                <option v-for="item in unselectedMembers" :value="item"> {{ item.name }} </option>
                            </select>
                            <div class="member-info">
                                <div class="selectedMember2" v-for="item in selectedMembers" :value="item.id">
                                    <div class="name-button">
                                        <div class="member-name">{{ item.name }}</div>
                                        <button class="remove-button" @click="removeParticipant(item.id)">x</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div v-else class="selectedMember1">
                            참여 멤버:
                            <div class="member-info">
                                <div v-for="item in selectedMembers" :value="item.id">
                                    <div>{{ item.name }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="submit">
                        <div v-if="modifyCheck">
                            <button class="btn btn-dark" v-if="!isReadOnly" @click="saveStudySchedule()">저장하기</button>
                            <button class="btn btn-dark" v-if="!isReadOnly" @click="removeStudySchedule()">삭제하기</button>
                            <button class="btn btn-dark" v-else @click="isReadOnly = false">수정하기</button>
                        </div>
                        <div v-else> </div>
                    </div>
                </div>
            </div>
        </div>

        <Footer></Footer>
    </div>


</template>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin: 30px;
    width: 100%;
}

.title {
    font-size: 30px;
    text-align: center;
    margin-top: 50px;
}

.info {
    align-items: center;
    width: 100%;
}

.info div {
    margin: 10px;
}

.content {
    margin-left: 10px;
}

.submit {
    display: flex;
    justify-content: center;
    padding: 20px;
}



.wrapper {
    margin-left: 12.5%;
    margin-right: 12.5%;
    width: 75%;
    display: grid;
}

.all {
    display: grid;
    grid-template-rows: 100px minmax(780px, auto) 200px;
    align-items: center;
}

.schedule-content {
    min-height: 300px;
}

.scheduleInfoModify {
    display: flex;
    justify-content: center;
    padding: 20px;
}

.selectedMember1 {
    display: flex;
    justify-content: wrap;
    align-items: center;
}

.selectedMember2 {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 5px;
}

.member-info {
    display: flex;
    align-items: center;
    margin-right: 10px;
}

/* .member-name {
    margin-right: 10px;
    멤버 이름과 삭제 버튼 사이의 간격을 설정합니다.
} */

.name-button {
    display: flex;
    align-items: center;
}

.name-button>div {
    margin-right: 5px;
}

.remove-button {
    /* background-color: transparent; 배경색을 투명하게 설정하여 버튼 스타일을 설정합니다. */
    /* border: none; 테두리를 없애줍니다. */
    cursor: pointer;
    /* 커서를 포인터로 변경하여 클릭 가능함을 나타냅니다. */
    /* color: red; 삭제 버튼의 색상을 설정합니다. */
}
</style>
