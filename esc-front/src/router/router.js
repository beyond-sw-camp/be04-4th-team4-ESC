import {createRouter, createWebHistory} from 'vue-router';
import StudyScheduleSave from '@/components/Schedule/StudyScheduleSave.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: () => import('../views/MainLogout.vue')
        },
        {
            path: '/login',
            component: () => import('../views/LoginPage.vue')
        },
        {
            path: '/after/login',
            component: () => import('../views/MainLogin.vue')
        },
        {
            path: '/regist',
            component: () => import('../views/Regist.vue')
        },
        {
            path: '/recruit-list',
            component: () => import('@/components/Recruit/RecruitList.vue')
        },
        {
            path: '/recruit/:id',
            component: () => import('@/components/Recruit/RecruitDetail.vue')
        },
        { // id에 해당하는 스터디 클럽
            path: '/studyclub/:id',
            component: () => import('@/views/StudyclubView.vue')
        },
        {
            path: '/studyclub-regist/:id',
            component: () => import('@/components/Studyclub/StudyclubRegist.vue')
        },
        {
            path: '/studyclub-modify/:id',
            component: () => import('@/components/Studyclub/StudyclubModify.vue')
        },
        {
            path: '/recruit-regist/:id',
            component: () => import('@/components/Recruit/RecruitRegist.vue')
        },
        {
            path: '/recruit-modify/:id',
            component: () => import('@/components/Recruit/RecruitModify.vue')
        },
        {
            path: '/chat/room/:id',
            component: () => import('@/views/ChatRoom.vue')
        },
        {
            path: '/chat/list',
            component: () => import('@/views/ChatRoomList.vue')
        },
        {
            path: '/member-schedule/:studyclubId/:memberId',
            component: () => import('@/components/Schedule/MemberScheduleView.vue')
        },
        {
            path: '/findId',
            component: () => import('../views/FindIdPage.vue')
        },
        {
            path: '/findIdResult/:email',
            component: () => import('../views/FindIdResultPage.vue')
        },
        {
            path: '/study-schedule/:studyclubId',
            component: () => import('@/components/Schedule/StudyScheduleView.vue')
        },
        {
            path: '/study-schedule/save/:studyclubId/:memberId/:start/:end',
            name: 'StudyScheduleSave',
            component: StudyScheduleSave,
        },
        {
            path: '/study-schedule/schedule/:id',
            component: () => import('@/components/Schedule/StudyScheduleInfo.vue')
        },
        {
            path: '/resetPasswordFinal/:email',
            component: () => import('../views/ResetPwdFinalPage.vue')
        },
        {
            path: '/resetPassword',
            component: () => import('../views/ResetPwdPage.vue')
        },
        {
            path: '/mypage/:id',
            component: () => import('@/components/MyPage/MyPage.vue')
        },
        {
            path: '/addstudylog/:id',
            component: () => import('@/components/StudyLog/AddStudyLog.vue')
        },
        {
            path: '/anotherpage/:id',
            component: () => import('@/components/MyPage/AnotherPage.vue')
        },
        {
            path: '/studylog/:id',
            component: () => import('@/components/StudyLog/StudyLogPage.vue')
        },
        {
            path: '/updateStudy/:id',
            component: () => import('@/components/StudyLog/UpdateStudyLog.vue')
        }
    ]
})

export default router;