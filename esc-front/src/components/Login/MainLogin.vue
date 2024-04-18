<template>
    <div>
        <p v-if="loaded && userInfo">{{ userInfo.name }}님을 환영합니다!</p>
        <p v-else>로딩 중...</p>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from "axios";

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

onMounted(() => {
    const token = localStorage.getItem('token');

    if (token) {
    fetchUserInfo(token);

    } else {
    console.error('토큰이 없습니다.');
    }
});


// const token = ref(localStorage.getItem('token'));
// console.log(token.value);

// const users = ref([]);

// onMounted(async () =>{
//         axios.get("http://localhost:8080/user/all",{
//             headers: {Authorization: `Bearer ${token.value}`}
//         })
//         .then(response => {
//             console.log(response);
//             users.value = response.data;
//             console.log(users.value);
//         })
//     });


</script>

<style scoped>

</style>