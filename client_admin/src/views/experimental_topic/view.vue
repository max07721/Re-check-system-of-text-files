<template>
	<el-main class="bg edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">

							<el-col v-if="user_group === '管理员' || $check_field('get','course_name') || $check_field('add','course_name') || $check_field('set','course_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="课程名称" prop="course_name">
								<el-select id="course_name" v-model="form['course_name']"
						v-if="user_group === '管理员' || (form['experimental_topic_id'] && $check_field('set','course_name')) || (!form['experimental_topic_id'] && $check_field('add','course_name'))">
						<el-option v-for="o in list_course_name" :key="o['course_name']" :label="o['course_name']"
							:value="o['course_name']">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','course_name')">{{form['course_name']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','experimental_topic') || $check_field('add','experimental_topic') || $check_field('set','experimental_topic')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="实验题目" prop="experimental_topic">
												<el-input id="experimental_topic" v-model="form['experimental_topic']" placeholder="请输入实验题目"
							  v-if="user_group === '管理员' || (form['experimental_topic_id'] && $check_field('set','experimental_topic')) || (!form['experimental_topic_id'] && $check_field('add','experimental_topic'))" :disabled="disabledObj['experimental_topic_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','experimental_topic')">{{form['experimental_topic']}}</div>
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
				field: "experimental_topic_id",
				url_add: "~/api/experimental_topic/add?",
				url_set: "~/api/experimental_topic/set?",
				url_get_obj: "~/api/experimental_topic/get_obj?",
				url_upload: "~/api/experimental_topic/upload?",

				query: {
					"experimental_topic_id": 0,
				},

				form: {
								"course_name":  '', // 课程名称
										"experimental_topic":  '', // 实验题目
											"experimental_topic_id": 0, // ID
						
				},
				disabledObj:{
								"course_name_isDisabled": false,
										"experimental_topic_isDisabled": false,
										},

					// 课程名称选项列表
				list_course_name: [""],
	
		
	
			}
		},
		methods: {


	
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
					bl = this.$check_action('/experimental_topic/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/experimental_topic/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/experimental_topic/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/experimental_topic/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/experimental_topic/view','get');
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
