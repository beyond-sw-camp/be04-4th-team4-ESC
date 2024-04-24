<template>
    <div class="container">
        <div class="boxdiv">
            <div>멤버</div>
            <div class="contentdiv">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item" v-for="userinfo in userinfos" :to="{ path: `/anotherpage/${userinfo.id}` }"
                    :key="userinfo.id">{{ userinfo.name }}  
                    </li>
                </ul>
            </div>
        </div>
        <div class="boxdiv">
            <div class="title-area">
                <div >스터디 일정 목록</div>
                <div class="added" style="margin-right: 10px; color: grey;" @click.stop="viewSchedule()">더보기 ></div>
            </div>
            <div class="contentdiv">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item" v-for="item in schedules">
                        <div class="name">{{ item.title }}</div>
                        <div class="date">{{ item.start }}&nbsp;&nbsp; ~ &nbsp;&nbsp;{{ item.end }}</div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="boxdiv">
        <div class="title-area">
                <div>로그</div>
                <div style=" font-size: 30px; text-align: right; margin:-11px 0px; margin-right: 15px;" @click="goToLogPage"> + </div>
        </div>
        <div class="contentdiv">
        <div class="accordion accordion-flush" id="accordionFlushExample">
               <div class="accordion-item" v-for="log in studylogs" :value="log" :key="log.id" :log="log">
                  <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" :data-bs-target="`#flush-collaps${log.id}`" aria-expanded="false" :aria-controls="`#flush-collapse${log.id}`">
                      {{ log.content }}
                    </button>
                  </h2>
                  <div :id="`flush-collaps${log.id}`" class="accordion-collapse collapse" data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">
                      <div class="apply-name">{{ log.contentInfo }}</div>
                    </div>
                    <hr>
                    <div style="text-align: center; margin-bottom: 15px; color: grey" @click="navigateTo(`/studyLog/${log.id}`)">자세히 보기 &nbsp; ></div>
                  </div>

              </div>
            </div>
        </div>
        </div>
</template>

<script setup>

import axios from 'axios';
import { useRouter, RouterLink, useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import moment from 'moment';

const userinfos = ref([]);

const route = useRoute();
const router = useRouter();

// 같은 id
const studyclubId = router.currentRoute.value.params.id;
const id = route.params.id;

const schedules = ref([]);
const userInfo = ref(null);
const loaded = ref(false);
const studylogs = ref([]);

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
                console.log(userInfo.value);
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

const fetchStudySchedules = async () => {
    try {
        const response = await axios.get(`/api/study-schedule/studyclub/${id}`);
        const data = response.data;
        console.log(data);

        const scheduleArray = [];

        data.studySchedules.forEach(studySchedule => {
            const startDate = moment(studySchedule.startDatetime).format('YYYY-MM-DDTHH:mm');
            const endDate = moment(studySchedule.endDatetime).format('YYYY-MM-DDTHH:mm');

            if (studySchedule.useStatus == 'Y') {
                const schedule = {
                    id: studySchedule.id,
                    title: studySchedule.title,
                    content: studySchedule.content,
                    start: startDate,
                    end: endDate,
                    useStatus: studySchedule.useStatus,
                    writerId: studySchedule.writerId,
                    studyclubId: studySchedule.studyclub
                }
                scheduleArray.push(schedule);
            }
        });

        schedules.value = scheduleArray;

    } catch (error) {
        console.error('There was a problem with the fetch operation:', error.message);
    }
};

const fetchStudyLogs = async () => {
    try {
          const response = await fetch(`/api/studyLog/findStudyclubLog/${id}`);

          if(!response.ok) {
              throw new Error('response is not ok');
          }

          const data = await response.json();
          studylogs.value = data;
          console.log(studylogs.value);

      } catch(error) {
          console.error('fetch error: ' + error.message);
      }
}

const navigateTo = (path) => {
        router.push(path);
    }

onMounted(async () => {

    try {
        const response1 = await axios.get(`/api/user/find-join-member-and-name/${studyclubId}`)
        // 요청이 성공했을 때 받은 데이터를 Vue 컴포넌트 데이터에 저장
        userinfos.value = response1.data
        console.log(userinfos);

        const response2 = await axios.get(`/api/studylog/find-studyclublog/${studyclubId}`)
        // 요청이 성공했을 때 받은 데이터를 Vue 컴포넌트 데이터에 저장
        studylogs.value = response2.data
        console.log(studylogs);

        
        const token = localStorage.getItem('token');
        
        if (token) {
            fetchUserInfo(token);
            
        } else {
            console.error('토큰이 없습니다.');
        }
        
        
    } catch (error) {
        console.error('데이터를 받아오는 중 에러 발생:', error);
    }
    
    await fetchStudySchedules(id);
    await fetchStudyLogs();
});



const studyschedules = ref([]);
function goToLogPage() {
    router.push(`/addstudylog/${studyclubId}`);
}

const viewSchedule = () => {
        router.push(`/study-schedule/${id}`);    
    };

    const viewCalendar = () => {
        router.push(`/member-schedule/${id}/${userInfo.value.id}`);    
    };

</script>

<style scoped>
.container {
    display: grid;
    grid-template-columns: 1fr 2fr;
    gap: 30px;
    margin-top: 30px;
    margin-bottom: 30px;
}
.contentdiv {
    flex-grow: 1;
    text-align: center;
    border: 1px solid black;
    box-sizing: border-box;
    padding: 20px;
    margin-top: 10px;
}
.boxdiv {
    display: flex;
    flex-direction: column;
}
.title-area {
    display: flex;
    justify-content: space-between
}
.log {
    display: flex;
    justify-content: space-between;
}
.date {
    font-size: 12px;
}
</style>