<script setup>
    import { reactive, ref, onMounted, isReactive } from 'vue';
    import RecruitCard from './RecruitCard.vue';
    import { useRouter } from 'vue-router';
    import axios from "axios";
    import Header from "@/components/Header/Header.vue";
    import Footer from "@/components/Footer/Footer.vue";
    
    const router = useRouter();
    const userInfo = ref(null);
    const loaded = ref(false); 
    let loginCheck = false;

    const navigateTo = (path) => {
        router.push(path);
    }

    const state = reactive({
        recruitList: [],
        category: []
    });

    const selectedCategory = ref();

    const fetchRecruitList = async() => {

        try {
            const response = await fetch("/api/recruit/list");

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.recruitList = data;

        } catch(error) {
            console.log('fetch error: ' + error.message);
        }
    };

    const fetchCategory = async() => {

        try {
            const response = await fetch('/api/studyclub/category');

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.category = data;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    };

    function makeStudyClub() {
        if( loginCheck == true) {
            router.push(`/studyclub-regist/${userInfo.value.id}`)
        } else {
            alert('로그인이 필요합니다.')
            return false;
        }
        
    }

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

    onMounted(async() => {
        await fetchRecruitList();
        await fetchCategory();

        const token = localStorage.getItem('token');

        if (token) {
        fetchUserInfo(token);
        loginCheck = true;
        } else {
        console.error('토큰이 없습니다.');
        }
    });

</script>

<template>
    <div class="all">
    <Header></Header>
    <div class="wrapper">
        <div class="filter">
            <form class="sort">
                <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                <label class="btn btn-outline-dark" for="btnradio1">토익</label>

                <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                <label class="btn btn-outline-dark" for="btnradio2">토스</label>

                <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
                <label class="btn btn-outline-dark" for="btnradio3">오픽

                </label>
                </div>
            </form>
            <div class="fixed" @click="makeStudyClub()">스터디클럽 생성하기</div>
        </div>
        <div>
            <RecruitCard class="card" v-for="recruit in state.recruitList" :key="recruit.id" :recruit="recruit"></RecruitCard>
        </div>
    </div>
    
    <Footer></Footer>
</div>
</template>

<style scoped>
    @font-face {
        font-family: "감탄로드돋움체 Bold";
        src: url("@/assets/fonts/감탄로드돋움체 Bold.ttf") format("truetype");
    }
    .all {
        display: grid;
        grid-template-rows: 100px minmax(780px, auto) 200px;
        align-items: center;
    }

    body * {
        font-family: "감탄로드돋움체 Bold", sans-serif;
    }
    .wrapper {
        margin-left:12.5%;
        margin-right:12.5%;
        width:75%;
        display: grid;
    }
   .filter {
        margin: 20px 15px;
        display: flex;
        justify-content: space-between;
        margin-top: 5%;
    }
    .card {
        margin: 10px 0px;
    }
    .category {
        text-align: center;
        width: 100px;
        height: 20px;
        font-size: 15px;
    }
    .fixed {
        border: 1px solid black;
        border-radius: 20px;
        background-color: aliceblue;
        padding: 20px;
        position: fixed;
        bottom: 50px;
        right: 50px;
        z-index: 999;
    }
    .sort {
        width: fit-content;
    }
    .filter {
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
    }
</style>