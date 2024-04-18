<script setup>
import { defineProps, reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
  recruit: Object,
});

const state = reactive({
  studyclub: {},
  category: "",
  goal: {},
  exam: {},
  writer: {}
});

const recruitDetail = (id) => {
  router.push(`/recruit/${id}`);
}

const fetchStudyclub = async(id) => {
  try {
    const response = await fetch(`/api/studyclub/detail/${id}`);

    if(!response.ok) {
        throw new Error('response is not ok');
      }

      const data = await response.json();

      state.studyclub = data;
      props.recruit["createdDate"] = splitDate(props.recruit["createdDate"]);

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
        state.exam["examDate"] = splitDate(state.exam["examDate"])

    } catch(error) {
        console.error('fetch error: ' + error.message);
    }
}

const fetchGoal = async(id) => {
  try {
      const response = await fetch(`/api/studyclub/study-goal/${id}`);

      if(!response.ok) {
          throw new Error('response is not ok');
      }

      const data = await response.json();
      state.goal = data;

  } catch(error) {
      console.error('fetch error: ' + error.message);
  }
}

const splitDate = (date) => {
   return date.substring(0, 10);
}

const hovering = ref(false);

const setHover = (value) => {
  hovering.value = value;
};

onMounted(async() => {
  await fetchStudyclub(props.recruit.clubId);
  await fetchCategory(state.studyclub.id);
  await fetchGoal(state.studyclub.id);
  await fetchExam(state.studyclub.id);
  await fetchWriter(state.studyclub.leaderId);
});
</script>

<template v-if="recruit.studyclub.name">
  <div class="card" @mouseover="setHover(true)" @mouseleave="setHover(false)" :class="{ 'hovering': hovering }" @click="recruitDetail(props.recruit.id)">
    <div class="card-header">
      <template v-if="recruit.recruitStatus=='N'">
        <div class="recruit-status-N">모집중</div>
      </template>
      <template v-else>
        <div class="recruit-status-Y">모집완료</div>
      </template>
      <div class="category">
        <span class="highlight">#</span> {{ state.category["studyName"] }} &nbsp;
        <span class="highlight">#</span> {{ state.goal["score"] }}
      </div>
    </div>
    <div class="content">
      <div class="title-area">
        <div class="title">{{ recruit.title }}</div>
        <div class="club-name">{{ state.studyclub["name"] }}</div>
        <div class="date">{{ state.exam["examDate"] }}</div>
      </div>
      <div class="user-area">
        <div class="user">{{ state.writer["nickname"] }}</div>
        <div class="count">{{ state.studyclub["memberCount"] }}명 / {{ state.studyclub["memberLimit"] }}명</div>
        <div class="date">{{ recruit.createdDate }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  border: 0.3px solid black;
  border-radius: 15px;
  padding: 25px;
}
.card.hovering {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.content {
  display: flex;
  justify-content: space-between;
}
.recruit-status-N {
  width: max-content;
  height: max-content;
  background-color: rgb(23, 116, 2);
  color: rgb(255, 255, 255);
  border-radius: 30px;
  padding: 5px;
  margin: 5px;
}
.recruit-status-Y {
  width: max-content;
  height: max-content;
  background-color: rgb(191, 0, 0);
  color: rgb(255, 255, 255);
  border-radius: 30px;
  padding: 5px;
  margin: 5px;
}
.title {
  font-size: 35px;
  margin: 5px;
  margin-bottom: 15px;
}
.club-name {
  font-size: 18px;
  margin: 5px;
}
.category {
  font-size: 20px;
  width: max-content;
  /* border: 1px solid gray; */
  border-radius: 5px;
  padding: 5px;
  margin: 5px;
}
.user {
  height: 40px;
  /* border: 1px solid black; */
  margin: 5px;
}
.count {
  text-align: right;
  margin: 5px;
}
.date {
  color: grey;
  margin: 5px;
}
.highlight {
  color: rgba(14, 94, 222, 0.884);
}
.user-area {
  display: grid;
  grid-template-rows: 0.5fr 0.5fr 0.5fr;
  justify-items: start;
}
</style>