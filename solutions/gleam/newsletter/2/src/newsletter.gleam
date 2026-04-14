import gleam/list
import gleam/result
import gleam/string
import gleam/uri
import simplifile

pub fn read_emails(path: String) -> Result(List(String), Nil) {
  path
  |> simplifile.read
  |> result.map(string.trim)
  |> result.map(string.split(_, "\n"))
  |> result.nil_error
}

pub fn create_log_file(path: String) -> Result(Nil, Nil) {
  path
  |> simplifile.create_file
  |> result.nil_error
}

pub fn log_sent_email(path: String, email: String) -> Result(Nil, Nil) {
  string.concat([email, "\n"])
  |> simplifile.append(path, _)
  |> result.nil_error
}

pub fn send_newsletter(
  emails_path: String,
  log_path: String,
  send_email: fn(String) -> Result(Nil, Nil),
) -> Result(Nil, Nil) {
  use _ <- result.try(create_log_file(log_path))
  use emails <- result.try(read_emails(emails_path))
  list.map(emails, fn(email) {
    use _ <- result.try(send_email(email))
    use _ <- result.try(log_sent_email(log_path, email))
    Ok(Nil)
  })
  Ok(Nil)
}
