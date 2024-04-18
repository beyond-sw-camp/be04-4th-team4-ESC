<template>
    <div class="chat-container">
        <ChatRoomCard v-for="room in roomList" :key="room.id" :room="room"/>
    </div>
</template>

<script setup>
    import { onMounted, inject } from "vue";
    import axios from 'axios';
    import ChatRoomCard from "./ChatRoomCard.vue";
    const roomList = inject('roomList');
    onMounted(async () => {
        const response = await axios.get(`http://localhost:30003/chat/room`);
        roomList.value = response.data;
    });
</script>

<style scoped>
</style>