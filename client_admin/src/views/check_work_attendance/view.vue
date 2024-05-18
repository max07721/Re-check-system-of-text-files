<template>
	<el-main class="bg edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">

							<el-col v-if="user_group === '管理员' || $check_field('get','teacher_account') || $check_field('add','teacher_account') || $check_field('set','teacher_account')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="教师账号" prop="teacher_account">
													<el-select v-if="user_group === '管理员' || (form['check_work_attendance_id'] && $check_field('set','teacher_account')) || (!form['check_work_attendance_id'] && $check_field('add','teacher_account'))" id="teacher_account" v-model="form['teacher_account']" :disabled="disabledObj['teacher_account_isDisabled']">
							<el-option v-for="o in list_user_teacher_account" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','teacher_account')" id="teacher_account" v-model="form['teacher_account']" :disabled="true">
							<el-option v-for="o in list_user_teacher_account" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','teachers_name') || $check_field('add','teachers_name') || $check_field('set','teachers_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="教师姓名" prop="teachers_name">
												<el-input id="teachers_name" v-model="form['teachers_name']" placeholder="请输入教师姓名"
							  v-if="user_group === '管理员' || (form['check_work_attendance_id'] && $check_field('set','teachers_name')) || (!form['check_work_attendance_id'] && $check_field('add','teachers_name'))" :disabled="disabledObj['teachers_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','teachers_name')">{{form['teachers_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','course_name') || $check_field('add','course_name') || $check_field('set','course_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="课程名称" prop="course_name">
												<el-input id="course_name" v-model="form['course_name']" placeholder="请输入课程名称"
							  v-if="user_group === '管理员' || (form['check_work_attendance_id'] && $check_field('set','course_name')) || (!form['check_work_attendance_id'] && $check_field('add','course_name'))" :disabled="disabledObj['course_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','course_name')">{{form['course_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','student_account') || $check_field('add','student_account') || $check_field('set','student_account')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="学生账号" prop="student_account">
													<el-select v-if="user_group === '管理员' || (form['check_work_attendance_id'] && $check_field('set','student_account')) || (!form['check_work_attendance_id'] && $check_field('add','student_account'))" id="student_account" v-model="form['student_account']" :disabled="disabledObj['student_account_isDisabled']">
							<el-option v-for="o in list_user_student_account" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','student_account')" id="student_account" v-model="form['student_account']" :disabled="true">
							<el-option v-for="o in list_user_student_account" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','student_name') || $check_field('add','student_name') || $check_field('set','student_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="学生姓名" prop="student_name">
												<el-input id="student_name" v-model="form['student_name']" placeholder="请输入学生姓名"
							  v-if="user_group === '管理员' || (form['check_work_attendance_id'] && $check_field('set','student_name')) || (!form['check_work_attendance_id'] && $check_field('add','student_name'))" :disabled="disabledObj['student_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','student_name')">{{form['student_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','class_time') || $check_field('add','class_time') || $check_field('set','class_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="上课时间" prop="class_time">
								<el-date-picker :disabled="disabledObj['class_time_isDisabled']" v-if="user_group === '管理员' || (form['check_work_attendance_id'] && $check_field('set','class_time')) || (!form['check_work_attendance_id'] && $check_field('add','class_time'))" id="class_time"
						v-model="form['class_time']" type="datetime" placeholder="选择日期时间">
					</el-date-picker>
					<div v-else-if="$check_field('get','class_time')">{{form['class_time']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','attendance_situation') || $check_field('add','attendance_situation') || $check_field('set','attendance_situation')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="考勤情况" prop="attendance_situation">
								<el-select id="attendance_situation" v-model="form['attendance_situation']"
						v-if="user_group === '管理员' || (form['check_work_attendance_id'] && $check_field('set','attendance_situation')) || (!form['check_work_attendance_id'] && $check_field('add','attendance_situation'))">
						<el-option v-for="o in list_attendance_situation" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','attendance_situation')">{{form['attendance_situation']}}</div>
							</el-form-item>
			</el-col>
					
	
	
	
	
	
	
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item>
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "check_work_attendance_id",
				url_add: "~/api/check_work_attendance/add?",
				url_set: "~/api/check_work_attendance/set?",
				url_get_obj: "~/api/check_work_attendance/get_obj?",
				url_upload: "~/api/check_work_attendance/upload?",

				query: {
					"check_work_attendance_id": 0,
				},

				form: {
								"teacher_account": 0, // 教师账号
										"teachers_name":  '', // 教师姓名
										"course_name":  '', // 课程名称
										"student_account": 0, // 学生账号
										"student_name":  '', // 学生姓名
										"class_time":  '', // 上课时间
										"attendance_situation":  '', // 考勤情况
											"check_work_attendance_id": 0, // ID
						
				},
				disabledObj:{
								"teacher_account_isDisabled": false,
										"teachers_name_isDisabled": false,
										"course_name_isDisabled": false,
										"student_account_isDisabled": false,
										"student_name_isDisabled": false,
										"class_time_isDisabled": false,
										"attendance_situation_isDisabled": false,
										},

	
					// 用户列表
				list_user_teacher_account: [],
				
		
		
					// 用户列表
				list_user_student_account: [],
				
		
						// 考勤情况选项列表
				list_attendance_situation: ['到场','缺席'],
	
	
			}
		},
		methods: {


	
	
				/**
			 * 获取教师用户用户列表
			 */
			async get_list_user_teacher_account() {
                // if(this.user_group !== "管理员" && this.form["teacher_account"] === 0) {
                //     this.form["teacher_account"] = this.user.user_id;
                // }
                var json = await this.$get("~/api/user/get_list?user_group=教师用户");
                if(json.result && json.result.list){
                    this.list_user_teacher_account = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					get_user_teacher_account(id){
				var obj = this.list_user_teacher_account.getObj({"user_id":id});
				var ret = "";
				if(obj){
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
				}
				return ret;
			},
			
	
			
	
			
	
				/**
			 * 获取学生用户用户列表
			 */
			async get_list_user_student_account() {
                // if(this.user_group !== "管理员" && this.form["student_account"] === 0) {
                //     this.form["student_account"] = this.user.user_id;
                // }
                var json = await this.$get("~/api/user/get_list?user_group=学生用户");
                if(json.result && json.result.list){
                    this.list_user_student_account = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					get_user_student_account(id){
				var obj = this.list_user_student_account.getObj({"user_id":id});
				var ret = "";
				if(obj){
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
				}
				return ret;
			},
			
	
			
	
			
	
		
			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
																	// 获取缓存数据附加
				form = $.db.get("form");
							$.push(this.form ,form);
																						
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
							}
						})
					})
				}
											        if (this.form["class_time"].indexOf("-")===-1){
            this.form["class_time"] = this.$toTime(parseInt(this.form["class_time"]),"yyyy-MM-dd hh:mm:ss")
        }
								$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){


																        if(this.form["class_time"]=="0000-00-00 00:00:00"){
          this.form["class_time"] = null;
        }
				if(parseInt(this.form["class_time"]) > 9999){
					this.form["class_time"] = this.$toTime(parseInt(this.form["class_time"]),"yyyy-MM-dd hh:mm:ss")
				}
						


			},

			/**
			 * 提交前验证事件
			 * @param {Object} 请求参数
			 * @return {String} 验证成功返回null, 失败返回错误提示
			 */
			submit_check(param) {
				let msg = null
																											return msg;
			},

			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/check_work_attendance/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/check_work_attendance/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/check_work_attendance/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/check_work_attendance/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/check_work_attendance/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},

		},
		created() {
					this.get_list_user_teacher_account();
												this.get_list_user_student_account();
											},
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}




</style>
