import Vue from 'vue';
import VueRouter from 'vue-router';
import index from '../views/index.vue';
import login from '../views/login.vue';
import forgot from '../views/forgot.vue';
import register from '../views/register.vue';
Vue.use(VueRouter)

const routes = [
	// 主页
	{
		path: '/',
		name: 'index',
		component: index,
		meta: {
			index: 0,
			title: '首页'
		}
	},

	// 登录
	{
		path: '/login',
		name: 'login',
		component: login,
		meta: {
			index: 0,
			title: '登录'
		}
	},

	// 注册
	{
		path: '/register',
		name: 'register',
		component: register,
		meta: {
			index: 0,
			title: '注册'
		}
	},

	// 忘记密码
	{
		path: '/forgot',
		name: "forgot",
		component: forgot,
		meta: {
			index: 0,
			title: '忘记密码'
		}
	},

	// 修改密码
	{
		path: '/user/password',
		name: "password",
		component: () => import("../views/user/password.vue"),
		meta: {
			index: 0,
			title: '修改密码'
		}
	},

	// 视频播放页
	{
		path: "/media/video",
		name: "video",
		component: () => import('../views/media/video.vue'),
		meta: {
			index: 0,
			title: "视频"
		}
	},

	// 音频播放页
	{
		path: "/media/audio",
		name: "audio",
		component: () => import('../views/media/audio.vue'),
		meta: {
			index: 0,
			title: "音频"
		}
	},








	// 学生用户路由
	{
		path: '/student_users/table',
		name: 'student_users_table',
		component: () => import('../views/student_users/table.vue'),
		meta: {
			index: 0,
			title: '学生用户列表'
		}
	},
	{
		path: '/student_users/view',
		name: 'student_users_view',
		component: () => import('../views/student_users/view.vue'),
		meta: {
			index: 0,
			title: '学生用户详情'
		}
	},
	// 教师用户路由
	{
		path: '/teacher_users/table',
		name: 'teacher_users_table',
		component: () => import('../views/teacher_users/table.vue'),
		meta: {
			index: 0,
			title: '教师用户列表'
		}
	},
	{
		path: '/teacher_users/view',
		name: 'teacher_users_view',
		component: () => import('../views/teacher_users/view.vue'),
		meta: {
			index: 0,
			title: '教师用户详情'
		}
	},
	// 课程信息路由
	{
		path: '/course_information/table',
		name: 'course_information_table',
		component: () => import('../views/course_information/table.vue'),
		meta: {
			index: 0,
			title: '课程信息列表'
		}
	},
	{
		path: '/course_information/view',
		name: 'course_information_view',
		component: () => import('../views/course_information/view.vue'),
		meta: {
			index: 0,
			title: '课程信息详情'
		}
	},
	// 选课信息路由
	{
		path: '/course_selection/table',
		name: 'course_selection_table',
		component: () => import('../views/course_selection/table.vue'),
		meta: {
			index: 0,
			title: '选课信息列表'
		}
	},
	{
		path: '/course_selection/view',
		name: 'course_selection_view',
		component: () => import('../views/course_selection/view.vue'),
		meta: {
			index: 0,
			title: '选课信息详情'
		}
	},

	// 考勤记录路由
	{
		path: '/check_work_attendance/table',
		name: 'check_work_attendance_table',
		component: () => import('../views/check_work_attendance/table.vue'),
		meta: {
			index: 0,
			title: '考勤记录列表'
		}
	},
	{
		path: '/check_work_attendance/view',
		name: 'check_work_attendance_view',
		component: () => import('../views/check_work_attendance/view.vue'),
		meta: {
			index: 0,
			title: '考勤记录详情'
		}
	},
	// 实验信息路由
	{
		path: '/experimental_information/table',
		name: 'experimental_information_table',
		component: () => import('../views/experimental_information/table.vue'),
		meta: {
			index: 0,
			title: '实验信息列表'
		}
	},
	{
		path: '/experimental_information/view',
		name: 'experimental_information_view',
		component: () => import('../views/experimental_information/view.vue'),
		meta: {
			index: 0,
			title: '实验信息详情'
		}
	},
	// 解答记录路由
	{
		path: '/answer_record/table',
		name: 'answer_record_table',
		component: () => import('../views/answer_record/table.vue'),
		meta: {
			index: 0,
			title: '解答记录列表'
		}
	},
	{
		path: '/answer_record/view',
		name: 'answer_record_view',
		component: () => import('../views/answer_record/view.vue'),
		meta: {
			index: 0,
			title: '解答记录详情'
		}
	},
	// 实验报告路由
	{
		path: '/experimental_report/table',
		name: 'experimental_report_table',
		component: () => import('../views/experimental_report/table.vue'),
		meta: {
			index: 0,
			title: '实验报告列表'
		}
	},
	{
		path: '/experimental_report/view',
		name: 'experimental_report_view',
		component: () => import('../views/experimental_report/view.vue'),
		meta: {
			index: 0,
			title: '实验报告详情'
		}
	},
	// 整体评价路由
	{
		path: '/overall_evaluation/table',
		name: 'overall_evaluation_table',
		component: () => import('../views/overall_evaluation/table.vue'),
		meta: {
			index: 0,
			title: '整体评价列表'
		}
	},
	{
		path: '/overall_evaluation/view',
		name: 'overall_evaluation_view',
		component: () => import('../views/overall_evaluation/view.vue'),
		meta: {
			index: 0,
			title: '整体评价详情'
		}
	},
	// 课程名称路由
	{
		path: '/course_name/table',
		name: 'course_name_table',
		component: () => import('../views/course_name/table.vue'),
		meta: {
			index: 0,
			title: '课程名称列表'
		}
	},
	{
		path: '/course_name/view',
		name: 'course_name_view',
		component: () => import('../views/course_name/view.vue'),
		meta: {
			index: 0,
			title: '课程名称详情'
		}
	},
	// 实验题目路由
	{
		path: '/experimental_topic/table',
		name: 'experimental_topic_table',
		component: () => import('../views/experimental_topic/table.vue'),
		meta: {
			index: 0,
			title: '实验题目列表'
		}
	},
	{
		path: '/experimental_topic/view',
		name: 'experimental_topic_view',
		component: () => import('../views/experimental_topic/view.vue'),
		meta: {
			index: 0,
			title: '实验题目详情'
		}
	},

	// 用户路由
	{
		path: '/user/table',
		name: 'user_table',
		component: () => import('../views/user/table.vue'),
		meta: {
			index: 0,
			title: '用户列表'
		}
	},
	{
		path: '/user/view',
		name: 'user_view',
		component: () => import('../views/user/view.vue'),
		meta: {
			index: 0,
			title: '用户详情'
		}
	},
	{
		path: '/user/info',
		name: 'user_info',
		component: () => import('../views/user/info.vue'),
		meta: {
			index: 0,
			title: '个人信息'
		}
	},
	// 用户组路由
	{
		path: '/user_group/table',
		name: 'user_group_table',
		component: () => import('../views/user_group/table.vue'),
		meta: {
			index: 0,
			title: '用户组列表'
		}
	},
	{
		path: '/user_group/view',
		name: 'user_group_view',
		component: () => import('../views/user_group/view.vue'),
		meta: {
			index: 0,
			title: '用户组详情'
		}
	},
	{
		path: '/similarity-result',
		name: 'SimilarityResult',
		component: () => import('../views/SimilarityResultPage/view.vue'),
		meta: {
			index: 0,
			title: '查重报告'
		}
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

router.beforeEach((to, from, next) => {
	let token = to.query.token;
	if (token){
		$.db.set("token",token,120);
	}
	next();
})

router.afterEach((to, from, next) => {
	let title = "实验管理系统-admin";
	document.title = title;
})

export default router
