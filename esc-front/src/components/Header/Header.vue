<template>
    <header class="header">
        <div class="logo">
            <img src="../../assets/finalLogo.png" @click="main()" class="logoimage">
        </div>
        <div class="menu">
            <span @click="navigateTo('/')">소개</span>
            <span @click="navigateTo('/recruit-list')">모집</span>
            <span @click="msg()">스터디클럽</span>
            <span @click="myPage()">마이페이지</span>
        </div>
        <div class="loginbtndiv" v-if="isLoggedIn">
            <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-wechat chat-icon"
                viewBox="0 0 16 16" @click="openChat">
                <path
                    d="M11.176 14.429c-2.665 0-4.826-1.8-4.826-4.018 0-2.22 2.159-4.02 4.824-4.02S16 8.191 16 10.411c0 1.21-.65 2.301-1.666 3.036a.32.32 0 0 0-.12.366l.218.81a.6.6 0 0 1 .029.117.166.166 0 0 1-.162.162.2.2 0 0 1-.092-.03l-1.057-.61a.5.5 0 0 0-.256-.074.5.5 0 0 0-.142.021 5.7 5.7 0 0 1-1.576.22M9.064 9.542a.647.647 0 1 0 .557-1 .645.645 0 0 0-.646.647.6.6 0 0 0 .09.353Zm3.232.001a.646.646 0 1 0 .546-1 .645.645 0 0 0-.644.644.63.63 0 0 0 .098.356" />
                <path
                    d="M0 6.826c0 1.455.781 2.765 2.001 3.656a.385.385 0 0 1 .143.439l-.161.6-.1.373a.5.5 0 0 0-.032.14.19.19 0 0 0 .193.193q.06 0 .111-.029l1.268-.733a.6.6 0 0 1 .308-.088q.088 0 .171.025a6.8 6.8 0 0 0 1.625.26 4.5 4.5 0 0 1-.177-1.251c0-2.936 2.785-5.02 5.824-5.02l.15.002C10.587 3.429 8.392 2 5.796 2 2.596 2 0 4.16 0 6.826m4.632-1.555a.77.77 0 1 1-1.54 0 .77.77 0 0 1 1.54 0m3.875 0a.77.77 0 1 1-1.54 0 .77.77 0 0 1 1.54 0" />
            </svg>
            <button type="button" class="logoutBtn" @click="logout()">logout</button>
        </div>
        <div class="loginbtndiv" v-else>
            <button type="button" class="signUpBtn" @click="signup()">sign up</button>
            <button type="button" class="signInBtn"  @click="login()">sign in</button>
        </div> 
        
    </header>
</template>

<script setup>
    import router from '@/router/router';
    import { ref, computed, watch, reactive, onMounted } from "vue";
    import { useRouter } from 'vue-router';
    import axios from "axios";

    function msg() {
        if(loginCheck == true) {
            alert('지금 바로 스터디클럽을 만들어 공부를 시작해봐요~')
            router.push(`/studyclub-regist/${userInfo.value.id}`);
        }
        else {
            alert('회원가입 후에 스터디클럽을 만들어보아요~')
            router.push('/regist');
        }
    }

    const token = ref(localStorage.getItem('token'));
    let loginCheck = false;

    // 로그인 여부 계산
    const isLoggedIn = ref(!!token.value);

    const navigateTo = (path) => {
        router.push(path);
    }

    function myPage() {
        if (loginCheck == true) {
            router.push(`/mypage/${userInfo.value.id}`)
        } else {
            alert('로그인이 필요합니다.')
        }
    } 

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

    // 페이지 로드 시 초기화
    onMounted(() => {
    // 로컬 스토리지에서 토큰 값을 가져와서 로그인 여부 갱신
        const token = localStorage.getItem('token');
        // isLoggedIn.value = !!token.value;
        if (token) {
        fetchUserInfo(token);
        loginCheck = true;
        } 
    });

    function login() {
        router.push('/login');
    }

    function signup() {
        router.push('/regist');
    }

    function main() {
        router.push('/');
    }

    function logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('email');
        token.value = null; // 토큰 값 갱신
        isLoggedIn.value = false; // 로그인 여부 갱신
        router.push('/login');
    }

    function openChat () {
        window.open("http://localhost:5173/chat/list", "chatroom", "width=600, height=700");    
    }


</script>

<style scoped>
@font-face {
    font-family: '감탄로드돋움체 Bold';
    src: url('@/assets/fonts/감탄로드돋움체 Bold.ttf') format('truetype');
}

.chat-icon {
    color: #515050;
    margin-right: 10%;
}

* {
    margin: 0;
    padding: 0;
    font-family: 'GmarketSansMedium';
}

.header {
    width: 100%;
    height: 100px;
    position: relative;
    top: 0;
    background-color: white;
    display: grid;
    grid-template-columns: 1fr 2fr 1fr;
    justify-items: center;
}

.loginbtndiv {
    width:100%;
    text-align: end;
}

.signInBtn {
    background-color: #515050;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    margin-top:53px;
    margin-right: 2%;
    
    
}

.signUpBtn {
    background-color: #515050;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    margin-top:53px;    
    margin-right: 2%;
}

.logoutBtn {
    background-color: #515050;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    margin-top:53px;
    margin-right: 2%;
    
    
}

.myPageBtn {
    background-color: #515050;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    font-style: bold;
    margin-top:53px;    
    margin-right: 2%;
}

.logoimage {
    width:100%;
    object-fit: contain;
}

.logo {
    width:120px;
    cursor: pointer;
    margin-top: 1%;
    margin-left: 2%;
    justify-self: start;
}

.menu {
    text-align: center;
    margin-top:65px;
    font-weight: bold;
    width: 100%;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    justify-items: center;
}
.menu span{
    font-family: '감탄로드돋움체 Bold', sans-serif;
    margin-left: 3%;
    cursor: pointer;
}
</style>