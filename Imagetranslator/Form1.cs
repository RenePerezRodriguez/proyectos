using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Tesseract;
using DarrenLee.Translator;

namespace Imagetranslator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void btnAbrirImagen_Click(object sender, EventArgs e)
        {
            if (openFileDialog1.ShowDialog() == DialogResult.OK) 
            {
                string strFileName = openFileDialog1.FileName;
                Bitmap imagen = new Bitmap(strFileName);
                pictureBox1.Image=(System.Drawing.Image)imagen;
                string nombreImagen = openFileDialog1.SafeFileName;

                var img = new Bitmap(openFileDialog1.FileName);
                var tesseract = new TesseractEngine(@"./tessdata", "eng", EngineMode.TesseractAndCube);
                var page = tesseract.Process(img);
                string let;
                let = page.GetText();
                rtxtLetras.Text = let;
                //TRADUCTOR
                string textotraducido = Translator.Translate(let, "en", "es");
                richTextBox1.Text = textotraducido;
            }
            //try
            //{

            //}
            //catch (Exception ex)
            //{
            //    MessageBox.Show(ex.Message);
            //}
        }
    }
}
