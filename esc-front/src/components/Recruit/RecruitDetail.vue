<script setup>
    import { reactive, onMounted, ref } from 'vue';
    import { useRoute } from 'vue-router';
    import axios from "axios";
    import router from '@/router/router';
    import Header from "@/components/Header/Header.vue";
    import Footer from "@/components/Footer/Footer.vue";

    const userInfo = ref(null);
    const loaded = ref(false); 
    const modifyCheck = ref(false);
    let loginCheck = false;

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
            if(state.recruit["writerId"] == userInfo.value.id){
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
    
    const route = useRoute();
    const id = route.params.id;

    const state = reactive({
      recruit: {},
      studyclub: {},
      category: {},
      exam: {},
      writer: {}
    });

    const fetchRecruit = async(id) => {
        try {
            const response = await fetch(`/api/recruit/detail/${id}`);
            
            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.recruit = data;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }

    const fetchStudyclub = async(id) => {
        try {
            const response = await fetch(`/api/studyclub/detail/${id}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.studyclub = data;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }

    const fetchCategory = async(id) => {
        try {
            const response = await fetch(`/api/studyclub/study-category/${id}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.category = data;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }

    const fetchExam = async(id) => {
        try {
                const response = await fetch(`/api/studyclub/study-exam/${id}`);

                if(!response.ok) {
                    throw new Error('response is not ok');
                }

                const data = await response.json();
                state.exam = data;
                state.exam["examDate"] = state.exam["examDate"].substring(0, 10);

            } catch(error) {
                console.error('fetch error: ' + error.message);
            }
    }

    const fetchWriter = async(id) => {
        try {
                const response = await fetch(`/api/user/${id}`);

                if(!response.ok) {
                    throw new Error('response is not ok');
                }

                const data = await response.json();
                state.writer = data;

            } catch(error) {
                console.error('fetch error: ' + error.message);
            }
    }

    const applyRecruit = async(userId, id) => {
        const postData = {
            userId: userInfo.value.id,      
            postId: id
        }
    
        try {
            const response = await fetch(`/api/recruit-apply/regist/${id}/${postData.userId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                    body: JSON.stringify(postData)             
            });
            if(!response) {
                throw new Error('Network response was not ok');
            }
            alert('신청되었습니다!');  
        } catch(error) {
            console.error('There was a problem with the fetch operation:', error.message);
        }
    }

    function replyCheck(userId, id) {
        if(loginCheck == true && userInfo.value.id != state.recruit["writerId"]) {
            applyRecruit(userId, id);
        } else if (loginCheck == true && userInfo.value.id == state.recruit["writerId"]) {
            alert('스터디그룹을 생성한 본인은 신청할 수 없습니다.');
            return false;
        }
        else {
            alert('로그인해주세요!');
        }
    }

    function modifyPage() {
        router.push(`/recruit-modify/${id}`)
    }

    onMounted(async() => {
        await fetchRecruit(id);
        await fetchStudyclub(state.recruit["clubId"]);
        await fetchCategory(state.studyclub["id"]);
        await fetchExam(state.studyclub["id"]);
        await fetchWriter(state.recruit["writerId"]);


        const token = localStorage.getItem('token');

        if (token) {
            fetchUserInfo(token);
            loginCheck=true;
        } else {
            loginCheck = false;
            console.error('토큰이 없습니다.'); 
        }

        
    });
</script>

<template>
    <div class="all">
    <Header></Header>
    <div class="wrapper">
        <div class="post-header">
            <div class="title-area">
                <div class="title">{{ state.recruit["title"] }}</div>
                <div class="createdDate">작성일:&nbsp; {{ state.recruit["createdDate"] }}</div>
            </div>
            <div class="writer-area">
                <div class="writer">{{ state.writer["nickname"] }}</div>
                <div class="chat" v-if="modifyCheck" @click="modifyPage()"><button class="modifybtn">수정하기</button></div>
            </div>
        </div>
        <hr>
        <div>
            <div class="section">
                <div class="section-title">🌟 스터디클럽 이름</div>
                <div class="section-content">{{ state.studyclub["name"] }}</div> 
            </div>
            <div class="section">
                <div class="section-title">🌈 스터디클럽 소개</div>
                <div class="section-content">{{ state.studyclub["introduce"] }}</div>
            </div>
            <div class="section">
                <div class="section-title">✅ 모집 내용</div>
                <div class="section-subtitle">모집 인원: {{ state.studyclub["memberLimit"] }}명 </div>
                <div class="section-subtitle" style="margin-bottom: 20px;">카테고리: {{ state.category["studyName"] }}</div>
                <div class="section-content">{{ state.recruit["content"] }}</div> 
            </div>
            <div class="section">
                <div class="section-title">📅 진행 기간</div>
                <div class="section-content">~ {{ state.exam["examDate"] }}까지</div> 
            </div>
            <hr>
            <div class="submit">
                <button class="applybtn" @click="replyCheck(userId, id)">신청하기</button>
            </div>
        </div>
    </div>
    <Footer></Footer>
    </div>
</template>

<style scoped>
    @font-face {
        font-family: '감탄로드돋움체 Bold';
        src: url('@/assets/fonts/감탄로드돋움체 Bold.ttf') format('truetype');
    }
    
    .wrapper {
        margin-left:12.5%;
        margin-right:12.5%;
        width:75%;
        display: grid;
    }
    .all {
        display: grid;
        grid-template-rows: 100px minmax(780px, auto) 200px;
        align-items: center;
    }

    .post-header {
        display: flex;
        justify-content: space-between;
        margin-top: 20px;
    }
    .title {
        text-align: center;
        font-size: 35px;
        margin: 10px;
        margin-bottom: 20px;
    }
    .createdDate {
        color: gray;
        margin: 10px;
    }
    .chat {
        margin: 10px;
    }
    .writer {
        align-items: center;
        height: 35px;
        margin: 10px;
    }
    .section {
        margin: 30px 20px;
        text-align: left;
    }
    .section-title {
        font-size: 20px;
        margin: 10px;
    }
    .section-subtitle {
        margin-left: 45px;
        margin-bottom: 10px;
    }
    .section-content {
        margin-left: 40px;
    }
    .submit {
        display: flex;
        justify-content: center;
        padding: 20px;
    }
    .modifybtn{
        background-color: #515050;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 12px;
        font-style: bold;
        width: 100%;
    }

    .applybtn {
        background-color: #515050;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 12px;
        font-style: bold;
        width:20%
    }

    .writer-area {
        display:grid;
        grid-template-rows: 1fr 1fr;
        align-items: center;
        text-align: center;
    }
</style>