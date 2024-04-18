<script setup>
    import { reactive, onMounted } from "vue";
    import { useRoute } from 'vue-router';
    import Header from "@/components/Header/Header.vue";
    import Footer from "@/components/Footer/Footer.vue";
    import router from "@/router/router";
    
    const route = useRoute();
    const id = route.params.id;

    const state = reactive({
        category: [],
        exams: [],
        goals: []
    })

    const studyclub = reactive({
        name: "",
        introduce: "",
        memberLimit: "",
        selectedCategory: "",
        selectedExam: "",
        selectedGoal: ""
    })

    const fetchCategory = async() => {

        try {
            const response = await fetch('http://localhost:30003/studyclub/category');

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.category = data;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    };

    const fetchExams = async() => {

        try {
            const response = await fetch(`http://localhost:30003/studyclub/exam/${studyclub.selectedCategory}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.exams = data;
            
        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }

    const fetchGoals = async() => {

        try {
            const response = await fetch(`http://localhost:30003/studyclub/goal/${studyclub.selectedCategory}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.goals = data;
            
        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }

    const fetchStudyclub = async(id) => {
        
        try {
            const response = await fetch(`http://localhost:30003/studyclub/detail/${id}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            
            studyclub.name = data.name;
            studyclub.introduce = data.introduce;
            studyclub.memberLimit = data.memberLimit;
            studyclub.selectedCategory = data.studyId;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    };

    const fetchStudyGoal = async(id) => {
        try {
            const response = await fetch(`http://localhost:30003/studyclub/study-goal/${id}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            studyclub.selectedGoal = data.id;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }

    const fetchStudyExam = async(id) => {
        try {
            const response = await fetch(`http://localhost:30003/studyclub/study-exam/${id}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            studyclub.selectedExam = data.id;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }
    
    const modifyStudyclub = async() => {
        
        const modifyData = {
            name: studyclub.name,
            introduce: studyclub.introduce, 
            memberLimit: studyclub.memberLimit,
            studyId: studyclub.selectedCategory,
            examId: studyclub.selectedExam,
            goalId: studyclub.selectedGoal
        }


        try {
            const response = await fetch(`http://localhost:30003/studyclub/modify/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(modifyData)
            });

            if(!response) {
                throw new Error('Network response was not ok');
            }
            alert('수정 되었습니다!')
        } catch(error) {
            console.error('There was a problem with the fetch operation:', error.message);
        }
    };

    const splitDate = (date) => {
        return date.slice(0, 10);
    };

    onMounted(async() => {
        await fetchCategory();
        await fetchStudyclub(id);
        await fetchStudyGoal(id);
        await fetchStudyExam(id);
    });
</script>

<template>
    <div class="all">
        <Header></Header>
        <div class="wrapper">
            <div class="container">
        <div class="title">스터디클럽 수정</div>
        <div class="info">
            <div class="name">스터디클럽 이름: 
                <input class="content" v-model="studyclub.name"/>
            </div>
            <div class="introduce">스터디클럽 소개: </div>
                <textarea class="content" cols="50" rows="5" v-model="studyclub.introduce"/>
            <div class="number">스터디클럽 정원: 
                <input type="number" class="content" v-model="studyclub.memberLimit"/>
            </div>
            <div class="category">스터디클럽 카테고리: 
                <select class="content" v-model="studyclub.selectedCategory" @change="fetchGoals(), fetchExams()">
                    <option v-for="item in state.category" :value="item.id"> {{ item.studyName }} </option>
                </select>
            </div>
            <div class="date">스터디클럽 시험일: 
                <select class="content" v-model="studyclub.selectedExam">
                    <option v-for="item in state.exams" :value="item.id"> {{ item.examDate.substring(0, 10) }} </option>
                </select>
            </div>
            <div class="goal">스터디클럽 목표 점수: 
                <select class="content" v-model="studyclub.selectedGoal">
                    <option v-for="item in state.goals" :value="item.id"> {{ item.score }} </option>
                </select>
            </div>
            <div class="submit" @click="modifyStudyclub">
                <button>수정하기</button>
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
    .title {
        font-size: 30px;
        text-align: center;
        margin-top: 50px;
    }
    .info {
        margin: 40px;
        align-items: center;
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
</style>