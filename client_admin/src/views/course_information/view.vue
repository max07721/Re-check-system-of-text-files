<template>
	<el-main class="bg edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">

							<el-col v-if="user_group === '管理员' || $check_field('get','teacher_account') || $check_field('add','teacher_account') || $check_field('set','teacher_account')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="教师账号" prop="teacher_account">
																		<div v-if="user_group !== '管理员'">
							{{ get_user_session_teacher_account(form['teacher_account']) }}
							<!--<el-input id="business_name" v-model="form['teacher_account']" placeholder="请输入教师账号"-->
							<!--v-if="user_group === '管理员' || (form['course_information_id'] && $check_field('set','teacher_account')) || (!form['course_information_id'] && $check_field('add','teacher_account'))" :disabled="disabledObj['teacher_account_isDisabled']"></el-input>-->
							<!--<div v-else-if="$check_field('get','teacher_account')">{{form['teacher_account']}}</div>-->
							<el-select v-if="user_group === '管理员' || (form['course_information_id'] && $check_field('set','teacher_account')) || (!form['course_information_id'] && $check_field('add','teacher_account'))" id="teacher_account" v-model="form['teacher_account']" :disabled="disabledObj['teacher_account_isDisabled']">
								<el-option v-for="o in list_user_teacher_account" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','teacher_account')" id="teacher_account" v-model="form['teacher_account']" :disabled="true">
								<el-option v-for="o in list_user_teacher_account" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="teacher_account" v-model="form['teacher_account']" :disabled="disabledObj['teacher_account_isDisabled']">
							<el-option v-for="o in list_user_teacher_account" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','teachers_name') || $check_field('add','teachers_name') || $check_field('set','teachers_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="教师姓名" prop="teachers_name">
												<el-input id="teachers_name" v-model="form['teachers_name']" placeholder="请输入教师姓名"
							  v-if="user_group === '管理员' || (form['course_information_id'] && $check_field('set','teachers_name')) || (!form['course_information_id'] && $check_field('add','teachers_name'))" :disabled="disabledObj['teachers_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','teachers_name')">{{form['teachers_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','course_name') || $check_field('add','course_name') || $check_field('set','course_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="课程名称" prop="course_name">
								<el-select id="course_name" v-model="form['course_name']"
						v-if="user_group === '管理员' || (form['course_information_id'] && $check_field('set','course_name')) || (!form['course_information_id'] && $check_field('add','course_name'))">
						<el-option v-for="o in list_course_name" :key="o['course_name']" :label="o['course_name']"
							:value="o['course_name']">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','course_name')">{{form['course_name']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','experimental_arrangement') || $check_field('add','experimental_arrangement') || $check_field('set','experimental_arrangement')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="实验安排" prop="experimental_arrangement">
								<el-select id="experimental_arrangement" v-model="form['experimental_arrangement']"
						v-if="user_group === '管理员' || (form['course_information_id'] && $check_field('set','experimental_arrangement')) || (!form['course_information_id'] && $check_field('add','experimental_arrangement'))">
						<el-option v-for="o in list_experimental_arrangement" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','experimental_arrangement')">{{form['experimental_arrangement']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','class_location') || $check_field('add','class_location') || $check_field('set','class_location')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="上课地点" prop="class_location">
												<el-input id="class_location" v-model="form['class_location']" placeholder="请输入上课地点"
							  v-if="user_group === '管理员' || (form['course_information_id'] && $check_field('set','class_location')) || (!form['course_information_id'] && $check_field('add','class_location'))" :disabled="disabledObj['class_location_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','class_location')">{{form['class_location']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','course_arrangement') || $check_field('add','course_arrangement') || $check_field('set','course_arrangement')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="课程安排" prop="course_arrangement">
								<el-input type="textarea" id="course_arrangement" v-model="form['course_arrangement']" placeholder="请输入课程安排"
						v-if="user_group === '管理员' || (form['course_information_id'] && $check_field('set','course_arrangement')) || (!form['course_information_id'] && $check_field('add','course_arrangement'))" :disabled="disabledObj['course_arrangement_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','course_arrangement')">{{form['course_arrangement']}}</div>
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
				field: "course_information_id",
				url_add: "~/api/course_information/add?",
				url_set: "~/api/course_information/set?",
				url_get_obj: "~/api/course_information/get_obj?",
				url_upload: "~/api/course_information/upload?",

				query: {
					"course_information_id": 0,
				},

				form: {
								"teacher_account": 0, // 教师账号
										"teachers_name":  '', // 教师姓名
										"course_name":  '', // 课程名称
										"experimental_arrangement":  '', // 实验安排
										"class_location":  '', // 上课地点
										"course_arrangement":  '', // 课程安排
											"course_information_id": 0, // ID
						
				},
				disabledObj:{
								"teacher_account_isDisabled": false,
										"teachers_name_isDisabled": false,
										"course_name_isDisabled": false,
										"experimental_arrangement_isDisabled": false,
										"class_location_isDisabled": false,
										"course_arrangement_isDisabled": false,
										},

	
					// 用户列表
				list_user_teacher_account: [],
						// 用户组
				group_user_teacher_account: "",
				
						// 课程名称选项列表
				list_course_name: [""],
	
						// 实验安排选项列表
				list_experimental_arrangement: ['是','否'],
	
		
		
	
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
					/**
			 * 获取教师用户用户组
			 */
			async get_group_user_teacher_account() {
							this.form["teacher_account"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=教师用户");
				if(json.result && json.result.obj){
					this.group_user_teacher_account = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_teacher_account(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_teacher_account.source_table+"/get_obj?"
				this.$get(url, user_id, function(res) {
					if (res.result && res.result.obj) {
						var arr = []
						for (let key in res.result.obj) {
							arr.push(key)
						}
						var arrForm = []
									for (let key in _this.form) {
							arrForm.push(key)
						}
												_this.form["teacher_account"] = id
									_this.disabledObj['teacher_account' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "teacher_account") {
			                      _this.form[arrForm[j]] = res.result.obj[arr[i]]
			                      _this.disabledObj[arrForm[j] + '_isDisabled'] = true
								  break;
								} else {
								  _this.disabledObj[arrForm[j] + '_isDisabled'] = true
								}
							  }
							}
						  }
						}
					}
				});
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
			 * 获取课程名称列表
			 */
			async get_list_course_name() {
				var json = await this.$get("~/api/course_name/get_list?");
				if(json.result && json.result.list){
					this.list_course_name = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
	
			
	
			
	
			
	
		
			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
																														
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
																$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){


																		


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
					bl = this.$check_action('/course_information/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/course_information/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/course_information/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/course_information/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/course_information/view','get');
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
					this.get_group_user_teacher_account();
									this.get_list_course_name();
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
