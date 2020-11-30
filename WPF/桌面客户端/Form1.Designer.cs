
namespace 桌面客户端
{
    partial class ServerManager
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ServerManager));
            this.Setting = new System.Windows.Forms.Button();
            this.RasPi1 = new System.Windows.Forms.Button();
            this.RasPi2 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // Setting
            // 
            resources.ApplyResources(this.Setting, "Setting");
            this.Setting.Name = "Setting";
            this.Setting.UseVisualStyleBackColor = true;
            // 
            // RasPi1
            // 
            resources.ApplyResources(this.RasPi1, "RasPi1");
            this.RasPi1.Name = "RasPi1";
            this.RasPi1.UseVisualStyleBackColor = true;
            // 
            // RasPi2
            // 
            resources.ApplyResources(this.RasPi2, "RasPi2");
            this.RasPi2.Name = "RasPi2";
            this.RasPi2.UseVisualStyleBackColor = true;
            // 
            // ServerManager
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.RasPi2);
            this.Controls.Add(this.RasPi1);
            this.Controls.Add(this.Setting);
            this.Name = "ServerManager";
            this.Load += new System.EventHandler(this.ServerManager_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button Setting;
        private System.Windows.Forms.Button RasPi1;
        private System.Windows.Forms.Button RasPi2;
    }
}

