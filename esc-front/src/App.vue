<script setup>
    import Header from "@/components/Header/Header.vue";
    import Footer from "@/components/Footer/Footer.vue";
    import { RouterLink, RouterView, useRouter } from 'vue-router';

    const router = useRouter();
    
    // function openChat() {
    //     const uniqueName = `chat_${new Date().getTime()}`;
        
    //     window.open(`http://localhost:5173/chat/list`, uniqueName, "width=700, height=700")
    // }

</script>

<template>
    
    <RouterView />
           
</template>


<style scoped>
@font-face {
    font-family: '감탄로드돋움체 Bold';
    src: url('@/assets/fonts/감탄로드돋움체 Bold.ttf') format('truetype');
}

body * {
    font-family: '감탄로드돋움체 Bold', sans-serif;
}



</style>