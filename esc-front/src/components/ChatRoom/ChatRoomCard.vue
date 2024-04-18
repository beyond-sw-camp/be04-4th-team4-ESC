<template>
    <div class="chatCard" @click="openChat(room.id)">
        <div class="listContent">
            <div id="content-title">
                <div id="titleNum">
                    <div id="chatRoomId">
                        #No.{{ room.id }}
                    </div>
                    <div id="chatRooName">
                        {{ room.roomName }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { defineProps } from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();
    const props = defineProps(['room']);
    const room = props.room;


    function openChat(roomId) {
        // const uniqueName = `chat_${new Date().getTime()}`;
        router.push(`/chat/room/${roomId}`)
        // window.open(`http://localhost:5173/chat/room/${roomId}`, 'uniqueName', "width=500, height=800")
    }
</script>

<style scoped>
.chatCard {
    display: flex;
    flex-direction: row;
    width: 100%;
    cursor: pointer;
    filter: blur(0.3px);
    border-color: #1c19e3;
}

.postCard :hover {
    cursor: pointer;
}


.listContent {
    padding: 0;
    margin: 0;
    border: 0.5px solid rgb(227, 224, 224);
    overflow: hidden;
    transition: border-color 0.3s;
    width: 100%;
}

.postCard:hover .listContent {
    filter: blur(0.3px);
    border-color: #d490e0;
}

#content-title {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    height: 50px;
    margin-left: 10px;
    width: 100%;
}

#titleNum {
    display: flex;
    flex-direction: row;
    align-items: flex-end;
}


#chatRooName {
    font-size: 18px;
    font-weight: bold;
    margin: 0 0 0 30px;
}

#chatRoomId {
    font-size: 18px;
    font-weight: bold;
    margin: 30 0 0 20px;        
    color: rgb(15, 9, 9);
}


#event-location {
    width: 400px;
}
</style>